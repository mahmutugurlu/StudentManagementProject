package com.tpe.student_management.repository.user;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.payload.response.user.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select u from User u where u.userRole.roleName = ?2") //?2 yerine :userRole
    Page<User> findAllByRole(Pageable pageable, String userRole);

    List<User> getUserByFirstNameContains(String name);
    
    @Query("select u from User u where u.id in :studentIds")
    List<User> findByIds(Long[] studentIds);

    @Query(value = "select count(u) from User u where u.userRole.role = ?1")
    long countAdmin(Role role);

    List<User> findAllByAdvisorTeacherId(Long id);
}
