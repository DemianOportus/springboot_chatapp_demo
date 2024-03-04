package com.dfm.chatapp.mapper;

import com.dfm.chatapp.dto.UserDTO;
import com.dfm.chatapp.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-07T09:07:04-0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Homebrew)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public UserDTO entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.enabled( user.isEnabled() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );
        userDTO.phoneNumber( user.getPhoneNumber() );
        userDTO.password( user.getPassword() );
        userDTO.email( user.getEmail() );
        userDTO.language( user.getLanguage() );
        userDTO.role( user.getRole() );
        userDTO.gender( user.getGender() );
        userDTO.doctorGenderPreference( user.getDoctorGenderPreference() );
        userDTO.patientCapacity( user.getPatientCapacity() );
        userDTO.hasDoctor( user.getHasDoctor() );
        userDTO.doctorId( user.getDoctorId() );
        userDTO.address( user.getAddress() );
        userDTO.kilometerRange( user.getKilometerRange() );
        userDTO.confirmationEmailsSent( user.getConfirmationEmailsSent() );
        userDTO.betaKey( user.getBetaKey() );

        return userDTO.build();
    }

    @Override
    public User dtoToEntity(UserDTO userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.enabled( userDto.isEnabled() );
        user.id( userDto.getId() );
        user.firstName( userDto.getFirstName() );
        user.lastName( userDto.getLastName() );
        user.phoneNumber( userDto.getPhoneNumber() );
        user.password( userDto.getPassword() );
        user.email( userDto.getEmail() );
        user.language( userDto.getLanguage() );
        user.role( userDto.getRole() );
        user.gender( userDto.getGender() );
        user.doctorGenderPreference( userDto.getDoctorGenderPreference() );
        user.patientCapacity( userDto.getPatientCapacity() );
        user.hasDoctor( userDto.getHasDoctor() );
        user.doctorId( userDto.getDoctorId() );
        user.address( userDto.getAddress() );
        user.kilometerRange( userDto.getKilometerRange() );
        user.confirmationEmailsSent( userDto.getConfirmationEmailsSent() );
        user.betaKey( userDto.getBetaKey() );

        return user.build();
    }

    @Override
    public List<User> dtoToEntity(Iterable<UserDTO> userDtos) {
        if ( userDtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>();
        for ( UserDTO userDTO : userDtos ) {
            list.add( dtoToEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> entityToDto(Iterable<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>();
        for ( User user : users ) {
            list.add( entityToDto( user ) );
        }

        return list;
    }
}
