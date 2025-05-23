package com.tpe.student_management.repository;

import com.tpe.student_management.entity.classes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
