package com.tpe.student_management.service.helpers;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.exception.BadRequestException;
import com.tpe.student_management.exception.ResourceNotFoundException;
import com.tpe.student_management.payload.messages.ErrorMessages;
import com.tpe.student_management.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
    private final UserRepository userRepository;

    // !!! isUserExist
    public User isUserExist(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.USER_ID_NOT_FOUND,
                        userId)));
    }

    public void checkBuiltIn(User user){
        if(Boolean.TRUE.equals(user.getBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_ACTION);
        }
    }

    public User isUserExistByUsername(String username){
        User user = userRepository.findByUsername(username);

        if(user.getId() == null){
            throw new ResourceNotFoundException(ErrorMessages.USER_ID_NOT_FOUND);
        }
        return user;
    }

    public void checkAdvisor(User user){
        if(Boolean.FALSE.equals(user.getIsAdvisor())){
            throw new BadRequestException(String.format(ErrorMessages.NOT_FOUND_ADVISOR_MESSAGE, user.getId()));
        }
    }
    // !!! Rol kontrolu yapan method
    public void checkRole(User user, Role roleType){
        if (!user.getUserRole().getRole().equals(roleType)) {
            throw new ResourceNotFoundException(
                    String.format(ErrorMessages.NOT_FOUND_USER_WITH_ROLE_MESSAGE, user.getId(),roleType));
        }
    }
}
