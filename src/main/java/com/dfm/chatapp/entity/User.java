package com.dfm.chatapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 20)
    private String lastName;

    @Column(name = "phone_number", nullable = false, unique = true)
    @NotEmpty
    @Size(max = 11)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(max = 120)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "doctor_gender_preference")
    private Gender doctorGenderPreference;

    @Column(name = "patient_capacity")
    private Integer patientCapacity;

    @Column(name = "has_doctor")
    private Boolean hasDoctor;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "address", nullable = false)
    @NotEmpty
    private String address;

    @Column(name = "kilometer_range")
    private Integer kilometerRange;

    @Column(name = "ver_emails_sent", nullable = false)
    private int confirmationEmailsSent = 0;

    @Column(name = "beta_key", nullable = false)
    @NotEmpty
    @NotNull
    @NotBlank
    private String betaKey;

}
