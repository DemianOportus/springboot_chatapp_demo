package com.dfm.chatapp.repository;

import com.dfm.chatapp.entity.Gender;
import com.dfm.chatapp.entity.Language;
import com.dfm.chatapp.entity.Role;
import com.dfm.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Extends CrudRepository for now. In future, will extend JpaRepository

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

    void deleteById(Long user_id);

    Optional<User> findById(Long user_id);

    List<User> findByRoleAndHasDoctor(Role patient, boolean b);

    List<User> findByRoleAndPatientCapacityGreaterThan(Role doctor, int i);

    List<User> findByRole(Role role);

    List<User> findByFirstNameAndLastNameAndRole(String firstName, String lastName, Role doctor);

    List<User> findByRoleAndHasDoctorAndDoctorGenderPreference(Role patient, boolean b, Gender gender);

    List<User> findByRoleAndGender(Role doctor, Gender gender);

    List<User> findByRoleAndGenderAndPatientCapacityGreaterThan(Role doctor, Gender doctorGenderPreference, int i);

    List<User> findByRoleAndLanguageAndPatientCapacityGreaterThan(Role doctor, Language language, int i);

    List<User> findByRoleAndGenderAndLanguageAndPatientCapacityGreaterThan(Role doctor, Gender doctorGenderPreference, Language language, int i);

    Optional<User> findByLastName(String lastName);

    User findByEmailIgnoreCase(String email);

    List<User> findByRoleAndHasDoctorAndDoctorId(Role role, boolean hasDoctor, Long doctorId);
    @Query("SELECT u FROM User u WHERE u.role = :role AND (u.language = :language OR u.language = 'BILINGUAL') AND u.patientCapacity > 0")
    List<User> findByRoleAndLanguageIncludingBilingualAndPatientCapacityGreaterThan(@Param("role") Role role, @Param("language") Language language);
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.gender = :gender AND (u.language = :language OR u.language = 'BILINGUAL') AND u.patientCapacity > 0")
    List<User> findByRoleAndGenderAndLanguageIncludingBilingualAndPatientCapacityGreaterThan(@Param("role") Role role, @Param("gender") Gender gender, @Param("language") Language language);


    // List<UserDTO> getUsers();

}
