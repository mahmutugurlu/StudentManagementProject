package com.tpe.student_management.payload.messages;

public class ErrorMessages {
    public static final String OLD_PASSWORD_MISMATCH = "The old password does not match the records.";

    public static final String NOT_PERMITTED_ACTION = "You are not allowed to do this operation.";

    public static final String USERNAME_ALREADY_IN_USE = "The username: %s is already in use! Please try another one.";

    public static final String EMAIL_ALREADY_IN_USE = "The email: %s is already in use! Please try another one.";

    public static final String SSN_ALREADY_IN_USED = "The ssn: %s is already in use!";

    public static final String PHONE_ALREADY_IN_USE = "The phone number: %s is already in use! Please try another one.";

    public static final String ROLE_NOT_FOUND = "Role not found!";

    public static final String ROLE_DEFINITION_NOT_FOUND = "No such role: %s exists";

    public static final String USER_ID_NOT_FOUND = "No user found with ID: %s";

    public static final String USERNAME_NOT_FOUND = "No user found with username: %s";

    public static final String NOT_FOUND_ADVISOR_MESSAGE = "Error: Advisor Teacher with id : %s not found";

    public static final String NOT_FOUND_USER_WITH_ROLE_MESSAGE = "Error: The role information of the user with id %s is not role: %s" ;


    private ErrorMessages(){}
}
