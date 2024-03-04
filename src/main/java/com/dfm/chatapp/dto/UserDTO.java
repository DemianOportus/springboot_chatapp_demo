package com.dfm.chatapp.dto;

import com.dfm.chatapp.entity.Gender;
import com.dfm.chatapp.entity.Language;
import com.dfm.chatapp.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private boolean enabled;
    @NotNull
    @NotBlank(message = "firstName is blank")
    private String firstName;

    @NotNull
    @NotBlank(message = "lastName is blank")
    private String lastName;

    @NotNull
    @NotBlank(message = "phoneNumber is blank")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "password is blank")
    private String password;

    @NotNull
    @NotBlank(message = "email is blank")
    @Email(message = "email is not valid")
    private String email;

    @NotNull
    private Language language;

    @NotNull
    private Role role;

    private Gender gender;
    private Gender doctorGenderPreference = Gender.NONE;
    private Integer patientCapacity;
    private Boolean hasDoctor;
    private Long doctorId;
    private String address;
    private Integer kilometerRange;
    private int confirmationEmailsSent = 0;
    @NotNull(message = "We need you to input the beta key")
    @NotBlank(message = "you need a beta key to join the current version")
    private String betaKey;

}
