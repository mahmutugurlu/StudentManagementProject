package com.tpe.student_management.service.user;

import com.tpe.student_management.entity.classes.user.UserRole;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.exception.ResourceNotFoundException;
import com.tpe.student_management.payload.messages.ErrorMessages;
import com.tpe.student_management.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRoleByRole(Role role){
        return userRoleRepository.findByRoleEnum(role).orElseThrow(() ->
                new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));//JPQL
    }

    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }

    public void saveRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }
}
