package com.tpe.student_management.service.validator;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.exception.ConflictException;
import com.tpe.student_management.payload.messages.ErrorMessages;
import com.tpe.student_management.payload.request.abstracts.AbstractUserRequest;
import com.tpe.student_management.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
    private final UserRepository userRepository;

    public void checkIfDuplicateProperty(String username, String email, String ssn, String phoneNumber){
        if (userRepository.existsByUsername(username)){ //existsByUsernameOrEmailOrSsnOrPhoneNumber
            throw new ConflictException(String.format(ErrorMessages.USERNAME_ALREADY_IN_USE, username));
        }
        if (userRepository.existsByEmail(email)){
            throw new ConflictException(String.format(ErrorMessages.EMAIL_ALREADY_IN_USE, email));
        }
        if (userRepository.existsBySsn(ssn)){
            throw new ConflictException(String.format(ErrorMessages.SSN_ALREADY_IN_USED, ssn));
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)){
            throw new ConflictException(String.format(ErrorMessages.PHONE_ALREADY_IN_USE, phoneNumber));
        }
    }

    public void checkUniqueProperties(User user, AbstractUserRequest abstractUserRequest){
        String updatedUsername = "";
        String updatedSnn = "";
        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanced = false;
        if(!user.getUsername().equalsIgnoreCase(abstractUserRequest.getUsername())){
            updatedUsername = abstractUserRequest.getUsername();
            isChanced = true;
        }
        if(!user.getSsn().equalsIgnoreCase(abstractUserRequest.getSsn())){
            updatedSnn = abstractUserRequest.getSsn();
            isChanced = true;
        }
        if(!user.getPhoneNumber().equalsIgnoreCase(abstractUserRequest.getPhoneNumber())){
            updatedPhone = abstractUserRequest.getPhoneNumber();
            isChanced = true;
        }
        if(!user.getEmail().equalsIgnoreCase(abstractUserRequest.getEmail())){
            updatedEmail = abstractUserRequest.getEmail();
            isChanced = true;
        }

        if(isChanced) {
            checkIfDuplicateProperty(updatedUsername, updatedSnn, updatedPhone, updatedEmail);
        }

    }
}
