package com.tpe.student_management.repository.user;

import com.tpe.student_management.entity.classes.user.UserRole;
import com.tpe.student_management.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("select r from UserRole r where r.role = ?1") //:role da kullanabilirdik
    Optional<UserRole> findByRoleEnum(Role role);
}
