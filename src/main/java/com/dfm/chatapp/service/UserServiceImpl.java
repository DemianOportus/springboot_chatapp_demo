package com.dfm.chatapp.service;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.entity.*;
import com.dfm.chatapp.exception.NotFoundException;
import com.dfm.chatapp.exception.ServiceException;
import com.dfm.chatapp.mapper.IUserMapper;
import com.dfm.chatapp.repository.ConfirmationTokenRepository;
import com.dfm.chatapp.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

// UserServiceImpl.java


@Service
public class UserServiceImpl implements UserService {


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderServiceImpl emailSender;
    private final GeoServiceImpl geoService;
    protected IUserMapper userMapper;
    @Value("${app.base-url-email-verification}")
    private String baseUrlEmailVerification;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;


    public UserServiceImpl(RestTemplate restTemplate, UserRepository userRepository, IUserMapper userMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder, EmailSenderServiceImpl emailSender, GeoServiceImpl geoService) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSender = emailSender;
        this.geoService = geoService;
    }

    /**
     * This method takes an Optional<User> entity and returns its DTO
     * representation.
     *
     * @param entity The Optional<User> entity to convert.
     * @param id     The id to set in the DTO representation.
     * @return The DTO representation of the given user entity.
     * @throws NotFoundException If the entity is not present.
     */
    static UserDTO unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) {
            User user = entity.get();
            return UserDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .language(user.getLanguage())
                    .role(user.getRole())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .hasDoctor(user.getHasDoctor())
                    .doctorId(user.getId())
                    .doctorGenderPreference(user.getDoctorGenderPreference())
                    .patientCapacity(user.getPatientCapacity())
                    .build();
        } else {
            throw new NotFoundException("Entity not present");
        }
    }

    // UserServiceImpl.java
    public boolean emailInDatabase(String email) {
        // Check if email is already in use
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.isPresent();
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) throws MessagingException {
        // Enable the user
        userDTO.setEnabled(true);
        // Encode the password
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        // Handle patient and doctor roles
        if (userDTO.getRole() == Role.PATIENT) {
            handlePatientRole(userDTO);
        } else if (userDTO.getRole() == Role.DOCTOR) {
            handleDoctorRole(userDTO);
        }

        // Save the updated user entity and return the UserDTO
        User updatedUser = userRepository.saveAndFlush(userMapper.dtoToEntity(userDTO));
        return userMapper.entityToDto(updatedUser);
    }


    private void handlePatientRole(UserDTO patient) throws MessagingException {
        if(accountDisabled(patient)) return;
        initializePatient(patient);
        List<User> doctors = findAvailableDoctors(patient);

        if (!doctors.isEmpty()) {
            Optional<User> doctorOptional = findDoctorWithLowestPatientCapacity(doctors);

            if (doctorOptional.isPresent()) {
                User doctor = doctorOptional.get();
                assignDoctorAndUpdateCapacity(patient, doctor);
                sendAssignmentEmails(patient, doctor);
            }
        }
    }

    private boolean accountDisabled(UserDTO userDTO) {
        return !(userDTO.isEnabled());
    }

    private void initializePatient(UserDTO patient) {
        patient.setPatientCapacity(null);
        patient.setHasDoctor(false);
        patient.setDoctorId((long) -1);
    }

    private List<User> findAvailableDoctors(UserDTO patient) {
        String bilingual = "BILINGUAL";

        if (patient.getLanguage().toString().equalsIgnoreCase(bilingual) && patient.getDoctorGenderPreference().equals(Gender.NONE)) {
            //find doctors regardless of language and gender
            return userRepository.findByRoleAndPatientCapacityGreaterThan(Role.DOCTOR, 0);
        } else if (patient.getLanguage().toString().equalsIgnoreCase(bilingual) && !patient.getDoctorGenderPreference().equals(Gender.NONE)) {
            //find doctors who are that gender regardless of language
            return userRepository.findByRoleAndGenderAndPatientCapacityGreaterThan(Role.DOCTOR, patient.getDoctorGenderPreference(), 0);
        } else if (!patient.getLanguage().toString().equalsIgnoreCase(bilingual) && patient.getDoctorGenderPreference().equals(Gender.NONE)) {
            //find doctors who speak their language or are bilingual regardless of gender
            return userRepository.findByRoleAndLanguageIncludingBilingualAndPatientCapacityGreaterThan(Role.DOCTOR, patient.getLanguage());
        } else if (!patient.getLanguage().toString().equalsIgnoreCase(bilingual) && !patient.getDoctorGenderPreference().equals(Gender.NONE)) {
            //find doctors who speak their language or are bilingual and have their gender preference
            return userRepository.findByRoleAndGenderAndLanguageIncludingBilingualAndPatientCapacityGreaterThan(Role.DOCTOR, patient.getDoctorGenderPreference(), patient.getLanguage());
        } else {
            return null;
        }
    }


    private Optional findDoctorWithLowestPatientCapacity(List<User> doctors) {
        return doctors.stream()
                .filter(d -> d.getPatientCapacity() > 0)
                .min(Comparator.comparingInt(User::getPatientCapacity));
    }

    private void assignDoctorAndUpdateCapacity(UserDTO patient, User doctor) {
        patient.setHasDoctor(true);
        patient.setDoctorId(doctor.getId());
        doctor.setPatientCapacity(doctor.getPatientCapacity() - 1);

        userRepository.save(doctor);
        userRepository.save(userMapper.dtoToEntity(patient));

    }

    private void sendAssignmentEmails(UserDTO patient, User doctor) throws MessagingException {
        String patientEmailBody = String.format(
                "Dear <b>%s</b>,<br><br>We are pleased to inform you that you have been successfully matched with <b>Dr. %s</b> for your medical consultation. "
                        + "We recommend you reach out to them at your earliest convenience, using the phone number provided: <b>%s</b> to arrange your initial appointment.<br><br>"
                        + "We are confident that you will find <b>Dr. %s</b>'s expertise valuable in managing your healthcare needs. "
                        + "Should you require any further assistance or have questions regarding this process, please do not hesitate to get in touch with our support team.<br><br>"
                        + "Thank you for choosing ChatApp as your healthcare partner. We look forward to assisting you on your wellness journey.<br><br>"
                        + "Best Regards,<br><b>The ChatApp Team</b>",
                patient.getFirstName(), doctor.getLastName(), formatPhoneNumber(doctor.getPhoneNumber()), doctor.getLastName()
        );


        emailSender.sendEmail(patient.getEmail(), "Matched with a doctor", patientEmailBody);

        if (doctor.getPatientCapacity() == 0) {
            List<User> assignedPatients = userRepository.findByRoleAndHasDoctorAndDoctorId(Role.PATIENT, true, doctor.getId());
            if (!assignedPatients.isEmpty()) {
                StringBuilder patientList = new StringBuilder();
                for (User doctorPatients : assignedPatients) {
                    patientList.append(String.format("<p><b>Patient %d:</b><br>", assignedPatients.indexOf(doctorPatients) + 1));
                    patientList.append(String.format("<b>Name:</b> %s %s<br>", doctorPatients.getFirstName(), doctorPatients.getLastName()));
                    patientList.append(String.format("<b>Phone number:</b> %s<br>", formatPhoneNumber(doctorPatients.getPhoneNumber())));
                    patientList.append(String.format("<b>Email:</b> %s</p><br>", doctorPatients.getEmail()));
                }

                String doctorEmailBody = "Dear <b>Dr. " + doctor.getLastName() + "</b>,<br><br>We are pleased to inform you that you have been successfully paired with the following patients:<br><br>"
                        + "<b>" + patientList.toString() + "</b><br><br>We expect that they will reach out to you soon to schedule their initial appointments. "
                        + "Please be prepared to receive their communications in the coming days.<br><br>"
                        + "Your expertise and dedication are greatly appreciated as we strive to provide exceptional care to our users. "
                        + "Should you need any further information or assistance with this process, please do not hesitate to contact our team.<br><br>"
                        + "Thank you for your valuable contribution to our platform, ChatApp. We are proud to have you as part of our healthcare team.<br><br>"
                        + "Best Regards,<br><b>The ChatApp Team</b>";

                emailSender.sendEmail(doctor.getEmail(), "New Patient Assignments", doctorEmailBody);

            }
        }
    }

    public String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {  // Check to make sure the phone number has the expected number of digits
            String areaCode = phoneNumber.substring(0, 3);
            String prefix = phoneNumber.substring(3, 6);
            String lineNumber = phoneNumber.substring(6, 10);

            return "+1 (" + areaCode + ")-" + prefix + "-" + lineNumber;
        }
        return phoneNumber;  // If the phone number is not the expected length, return it as is
    }


    private void handleDoctorRole(UserDTO doctorDTO) {
        if(accountDisabled(doctorDTO)) return;
        // Initialize patient capacity if it is not provided
        int patientCapacity = doctorDTO.getPatientCapacity() == null ? 0 : doctorDTO.getPatientCapacity();

        // Set the patient capacity to 0 if the doctor does not want to take any patients
        if (patientCapacity <= 0) {
            doctorDTO.setPatientCapacity(0);
            return;
        }

        // Find patients who don't have a doctor and match them with the new doctor
        List<User> patients = getUnassignedPatients();
        int remainingCapacity = matchDoctorWithPatients(doctorDTO, patients, patientCapacity);

        // Find more doctors with patient capacity and match them with the remaining patients
        while (remainingCapacity > 0 && patients.size() > 0) {
            List<User> moreDoctors = userRepository.findByRoleAndPatientCapacityGreaterThan(Role.DOCTOR, 0);
            if (moreDoctors.isEmpty()) {
                break;
            }

            moreDoctors.sort(Comparator.comparingInt(User::getPatientCapacity));
            User anotherDoctor = moreDoctors.get(0);
            UserDTO anotherDoctorDTO = userMapper.entityToDto(anotherDoctor);
            remainingCapacity = matchDoctorWithPatients(anotherDoctorDTO, patients, remainingCapacity);

            userRepository.save(userMapper.dtoToEntity(anotherDoctorDTO));
            patients = getUnassignedPatients();
        }

        doctorDTO.setPatientCapacity(remainingCapacity);
    }

    /**
     * Matches a doctor with patients based on capacity and gender preferences.
     *
     * @param doctor   The doctor to match with patients.
     * @param patients The list of patients to match with the doctor.
     * @param capacity The maximum number of patients the doctor can take on.
     * @return The remaining capacity after matching the doctor with patients.
     */
    private int matchDoctorWithPatients(UserDTO doctor, List<User> patients, int capacity) {
        int remainingCapacity = capacity;
        List<User> updatedPatients = new ArrayList<>();
        Iterator<User> patientIterator = patients.iterator();

        // Iterate through the list of patients and try to match them with the doctor
        while (patientIterator.hasNext() && remainingCapacity > 0) {
            User patient = patientIterator.next();
            if (!patient.getHasDoctor() && patient.isEnabled() ){
                // If the patient hasn't been matched with a doctor yet
                if (patient.getDoctorGenderPreference() != null && !patient.getDoctorGenderPreference().equals(Gender.NONE) && !doctor.getGender().equals(patient.getDoctorGenderPreference())) {
                    // If the patient has a gender preference for their doctor and the doctor doesn't match that preference, skip this patient
                    continue;
                }
                /* BAD MATCHES BASED ON LANGUAGE ARE
                PATIENT -- DOCTOR
                ENG --> FRE
                FRE --> ENG
                all other matches are good*/
                if (patient.getLanguage().equals(Language.ENGLISH) && doctor.getLanguage().equals(Language.FRENCH))
                    continue;
                if (patient.getLanguage().equals(Language.FRENCH) && doctor.getLanguage().equals(Language.ENGLISH))
                    continue;
                double distance = geoService.calculateDistance(patient.getAddress(), doctor.getAddress());
                if (distance > patient.getKilometerRange()) continue;

                // Match the patient with the doctor
                patient.setHasDoctor(true);
                patient.setDoctorId(doctor.getId());
                updatedPatients.add(patient);
                patientIterator.remove(); // Remove the matched patient from the list
                remainingCapacity--;

                // Save patients in batches for better performance
                int BATCH_SIZE = 50;
                if (updatedPatients.size() >= BATCH_SIZE) {
                    userRepository.saveAll(updatedPatients); // Save the batch of matched patients to the database
                    updatedPatients.clear(); // Clear the list to make room for more patients
                }
            }
        }

        // Save remaining patients in the batch
        if (!updatedPatients.isEmpty()) {
            userRepository.saveAll(updatedPatients); // Save the remaining batch of matched patients to the database
        }

        return remainingCapacity; // Return the remaining capacity after matching the doctor with patients
    }


    private List<User> getUnassignedPatients() {
        return userRepository.findByRoleAndHasDoctor(Role.PATIENT, false);
    }

    @Override
    public UserDTO updateUser(UserDTO userToUpdate) throws NotFoundException {
        // Find the existing user
        User existingUser = userRepository.findById(userToUpdate.getId()).orElse(null);
        if (existingUser == null) {
            // The user does not exist
            throw new NotFoundException("User with id " + userToUpdate.getId() + " not found");
        }

        // Entity for persisting database. Manipulation is to the DTO
        UserDTO updatedUserDto = userMapper.entityToDto(existingUser);

        // Extract values into variables
        String firstName = userToUpdate.getFirstName();
        String lastName = userToUpdate.getLastName();
        String phoneNumber = userToUpdate.getPhoneNumber();
        String email = userToUpdate.getEmail();
        Role role = userToUpdate.getRole();

        // Update the user's fields AKA "set dirty fields"
        if (firstName != null) {
            updatedUserDto.setFirstName(firstName);
        }
        if (lastName != null) {
            updatedUserDto.setLastName(lastName);
        }
        if (phoneNumber != null) {
            updatedUserDto.setPhoneNumber(phoneNumber);
        }
        if (email != null) {
            updatedUserDto.setEmail(email);
        }
        if (role != null) {
            updatedUserDto.setRole(role);
        }

        return updatedUserDto;
    }


    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public UserDTO getUserById(Long id) throws ServiceException {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("User not found with id: " + id, HttpStatus.NOT_FOUND));
        return userMapper.entityToDto(userEntity);
    }

    /**
     * This method retrieves the user with the given first and last name and returns its DTO
     * representation.
     *
     * @param firstName The first name of the user to retrieve.
     * @param lastName  The last name of the user to retrive.
     * @return The DTO representation of the retrieved user.
     * @throws ServiceException If there was an error retrieving the user.
     */
    @Override
    public UserDTO getUser(String firstName, String lastName) throws ServiceException {
        // Retrieve the user entity with the given first and last name from the repository
        Optional<User> user = userRepository.findByFirstNameAndLastName(firstName, lastName);

        // Return the DTO representation of the retrieved user entity
        return unwrapUser(user, 404L);
    }

    @Override
    public UserDTO getUser(String lastName) throws ServiceException {
        // Retrieve the user entity with the given last name from the repository
        Optional<User> user = userRepository.findByLastName(lastName);

        // Return the DTO representation of the retrieved user entity
        return unwrapUser(user, 404L);
    }

    @Override
    public Iterable<UserDTO> getDoctors() {
        List<User> doctors = userRepository.findByRole(Role.DOCTOR);
        return userMapper.entityToDto(doctors);
    }

    @Override
    public Iterable<UserDTO> getPatients() {
        List<User> patients = userRepository.findByRole(Role.PATIENT);
        return userMapper.entityToDto(patients);
    }

    @Override
    public ResponseEntity<String> registerUser(UserDTO userDto) throws MessagingException {
        // Check if the email is already in the database
        if (emailInDatabase(userDto.getEmail())) {
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }
        /*
        ************************************************************************************
        THIS FOLLOWING LINE ONLY APPLIES FOR THE BETA AND WILL BE REMOVED IN FUTURE VERSIONS
        IT IS A PRIVATE KEY THAT WILL BE REQUIRED WHEN CREATING AN ACCOUNT, AS MEANS
        TO CONTROL THE PEOPLE WHO CREATE AN ACCOUNT. WE WANT THE BETA TO BE CLOSED AND CONTROLLED.
        */
        if (!userDto.getBetaKey().equalsIgnoreCase("SALTOFTHEEARTH")) {
            return new ResponseEntity<>("Beta key is incorrect", HttpStatus.BAD_REQUEST);
        }
        //**********************************************************************************


        // Map the UserDTO to a User entity and save it to the repository
        User user = userMapper.dtoToEntity(userDto);
        userRepository.save(user);

        // Create a new confirmation token for the user
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        // Generate the email confirmation URL using the token
        String confirmationUrl = String.format("%sapi/account/confirm-account?token=%s", baseUrlEmailVerification, confirmationToken.getConfirmationToken());

        // Send the email confirmation to the user's email
        String emailBody = "Dear <b>" + userDto.getFirstName() + "</b>,<br><br>"

                + "Thank you for registering on Chat App, your first step to matching with a dedicated family doctor. We are excited to help facilitate a valuable and convenient connection between you and your healthcare provider.<br><br>"
                + "To finalize your registration and activate your account, please <b>click</b> the following URL:<br><br>"
                + "<b>" + confirmationUrl + "</b><br><br>"
                + "Please note that for the security of your information, <b>this confirmation link will expire after "+ confirmationToken.getExpirationInMinutes() +" minutes</b>. We recommend you to complete the account activation process as soon as possible.<br><br>"
                + "Once your account is activated, you will immediately start using Chat App to get matched with doctors.<br><br>"
                + "If you have any questions or experience any issues during the activation process, please do not hesitate to reach out to our customer support team.<br><br>"
                + "Thank you once again for choosing Chat App. We look forward to serving your healthcare needs.<br><br>"
                + "Best regards,<br>"
                + "<b>The Chat App Team</b>";

        emailSender.sendEmail(
                userDto.getEmail(),
                "Chat App Account Confirmation Request",
                emailBody
        );


        // Return a success response
        return new ResponseEntity<>("Good. Please confirm email", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> handleConfirmationRequest(String confirmationToken) throws MessagingException {
        // Find the token in the repository
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        // If the token is not found, return an error response
        if (token == null) {
            return new ResponseEntity<>("User's email not confirmed because the token was null", HttpStatus.BAD_REQUEST);
        }

        // If the token is not expired, enable the user and save it to the repository
        if (!token.isExpired()) {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            createUser(userMapper.entityToDto(user));
            return new ResponseEntity<>("User Successfully created", HttpStatus.CREATED);
        }

        User user = token.getUser();
        // Check if we already sent 3 emails total (2 because it starts at 0, so 0, 1, 2 = 3 attempts of email)
        if (user.getConfirmationEmailsSent() >= 2) {
            userRepository.delete(user);
            return new ResponseEntity<>("Account deleted due to too many confirmation attempts", HttpStatus.BAD_REQUEST);
        }

        // Create a new token and save it
        ConfirmationToken newToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(newToken);

        // Update the user's confirmation email counter
        user.setConfirmationEmailsSent(user.getConfirmationEmailsSent() + 1);
        userRepository.save(user);

        // Token expired, so send a new confirmation email if user wants to get a new email to confirm his account creation
        String newConfirmationUrl = String.format("%sapi/account/confirm-account?token=%s", baseUrlEmailVerification, newToken.getConfirmationToken());
        String emailBody = "Dear " + user.getFirstName() + ",<br><br>"
                + "We noticed that the previous link expired when activating your Chat App account. Perhaps you were busy or the previous email got lost in your inbox. Whatever the reason, we understand and are here to assist.<br><br>"
                + "To ensure you can utilize our services and get matched with your preferred user at the earliest, we have generated a new confirmation link for you. To activate your account, please click the following URL:<br><br>"
                + newConfirmationUrl + "<br><br>"
                + "Please note that for the security of your information, this confirmation link will expire after "+ newToken.getExpirationInMinutes() +" minutes. We recommend you complete the account activation process as soon as possible.<br><br>"
                + "If you have any questions or need any further assistance, please feel free to reach out to our customer support team. We're here to help.<br><br>"
                + "We look forward to having you on the Chat App platform and serving your healthcare needs.<br><br>"
                + "Best regards,<br>"
                + "<b>The Chat App Team</b>";
        String attemptNumber = "";
        if(user.getConfirmationEmailsSent()+1 == 1 ) attemptNumber = "First";
        else if (user.getConfirmationEmailsSent()+1 == 2) attemptNumber = "Second";
        else if (user.getConfirmationEmailsSent()+1 == 3) attemptNumber = "Last";

        emailSender.sendEmail(
                user.getEmail(),
                "Chat App Account Confirmation - "+attemptNumber+" Attempt",
                emailBody
        );

        return new ResponseEntity<>(
                "The previous confirmation link has expired. We have sent a new link to your " +
                        "registered email address. Please check your inbox and follow the instructions to activate " +
                        "your Chat App account. If you need further assistance, please contact our customer support.",
                HttpStatus.BAD_REQUEST);

    }


    @Override
    public Iterable<UserDTO> getAllUsers() {
        Iterable<UserDTO> findAll = userMapper.entityToDto(userRepository.findAll());
        return findAll;
    }

    @Override
    public boolean unsubscribeUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }



}
