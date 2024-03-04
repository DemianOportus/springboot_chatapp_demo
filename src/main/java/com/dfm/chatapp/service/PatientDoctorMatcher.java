package com.dfm.chatapp.service;

import com.dfm.chatapp.entity.Gender;
import com.dfm.chatapp.entity.Language;
import com.dfm.chatapp.entity.Role;
import com.dfm.chatapp.entity.User;
import com.dfm.chatapp.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Component
public class PatientDoctorMatcher {

    private final UserRepository userRepository;
    private final EmailSenderServiceImpl emailSender;
    private final GeoServiceImpl geoService;

    public PatientDoctorMatcher(UserRepository userRepository, EmailSenderServiceImpl emailSender, GeoServiceImpl geoService) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.geoService = geoService;
    }

    public void matchDoctorsWithPatients() {
        // Find patients who don't have a doctor
        List<User> patients = userRepository.findByRoleAndHasDoctor(Role.PATIENT, false);

        // Find doctors with available patient capacity
        List<User> doctors = userRepository.findByRoleAndPatientCapacityGreaterThan(Role.DOCTOR, 0);

        // Sort the doctors by patient capacity in ascending order
        doctors.sort(Comparator.comparingInt(User::getPatientCapacity));

        List<User> updatedPatients = new ArrayList<>();
        List<User> updatedDoctors = new ArrayList<>();

        // Iterate over patients and match them with the doctors with the lowest patient capacity
        for (User doctor : doctors) {
            matchPatients(doctor, patients);
            updatedDoctors.add(doctor);
        }

        userRepository.saveAll(updatedDoctors);

        // Match patients with preferred doctor gender
        matchPreferredDoctorGenderPatients(patients);

        // Add remaining patients to the waiting list
        updateRemainingPatients(patients);
    }

    private void matchPatients(User doctor, List<User> patients) {
        int batchSize = 50; // Set batch size for saving patients
        List<User> batchPatients = new ArrayList<>();

        Iterator<User> patientIterator = patients.iterator();
        while (patientIterator.hasNext()) {
            if (doctor.getPatientCapacity() <= 0) {
                break;
            }

            User patient = patientIterator.next();

            /* BAD MATCHES BASED ON LANGUAGE ARE
            PATIENT -- DOCTOR
            ENG --> FRE
            FRE --> ENG
            all other matches are good */
            if (patient.getLanguage().equals(Language.ENGLISH) && doctor.getLanguage().equals(Language.FRENCH))
                continue;
            if (patient.getLanguage().equals(Language.FRENCH) && doctor.getLanguage().equals(Language.ENGLISH))
                continue;
            double distance = geoService.calculateDistance(patient.getAddress(), doctor.getAddress());
            if (distance > patient.getKilometerRange()) continue;

            // Check if the patient has no preference or if the doctor's gender matches the patient's preference
            if (patient.getDoctorGenderPreference().equals(Gender.NONE) || doctor.getGender().equals(patient.getDoctorGenderPreference())) {
                patient.setHasDoctor(true);
                patient.setDoctorId(doctor.getId());
                doctor.setPatientCapacity(doctor.getPatientCapacity() - 1);

                batchPatients.add(patient);
                if (batchPatients.size() >= batchSize) {
                    userRepository.saveAll(batchPatients);
                    batchPatients.clear();
                }

                patientIterator.remove(); // Remove the patient from the list
            }
        }

        // Save remaining patients in the batch
        if (!batchPatients.isEmpty()) {
            userRepository.saveAll(batchPatients);
        }
    }


    private void updateRemainingPatients(List<User> patients) {
        for (User patient : patients) {
            if (!patient.getHasDoctor()) {
                patient.setDoctorId(null);
            }
        }
        userRepository.saveAll(patients);
    }

    private void matchPreferredDoctorGenderPatients(List<User> patients) {
        for (Gender gender : Gender.values()) {
            if (gender.equals(Gender.NONE)) {
                continue;
            }

            List<User> preferredGenderDoctors = userRepository.findByRoleAndGender(Role.DOCTOR, gender);

            for (User doctor : preferredGenderDoctors) {
                // Find unmatched patients with a preference for the specific gender doctor
                List<User> preferencePatients = userRepository.findByRoleAndHasDoctorAndDoctorGenderPreference(Role.PATIENT, false, gender);

                matchPatients(doctor, preferencePatients);
            }
        }
    }
}
