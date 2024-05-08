package com.joshuadias.moneyplannerapi.dto.requests.appUser;

public interface AppUserRequestValidationConstants {
    int FIRST_NAME_MAX_SIZE = 50;
    int LAST_NAME_MAX_SIZE = 50;
    int EMAIL_MAX_SIZE = 50;
    int PASSWORD_MAX_SIZE = 50;
    String FIRST_NAME_NOT_BLANK_MESSAGE = "First name is required";
    String LAST_NAME_NOT_BLANK_MESSAGE = "Last name is required";
    String EMAIL_NOT_BLANK_MESSAGE = "Email is required";
    String PASSWORD_NOT_BLANK_MESSAGE = "Password is required";
    String FIRST_NAME_MAX_SIZE_MESSAGE = "First name must have a maximum of " + FIRST_NAME_MAX_SIZE + " characters";
    String LAST_NAME_MAX_SIZE_MESSAGE = "Last name must have a maximum of " + LAST_NAME_MAX_SIZE + " characters";
    String EMAIL_MAX_SIZE_MESSAGE = "Email must have a maximum of " + EMAIL_MAX_SIZE + " characters";
    String PASSWORD_MAX_SIZE_MESSAGE = "Password must have a maximum of " + PASSWORD_MAX_SIZE + " characters";
    String EMAIL_MUST_BE_VALID = "Email must be valid";
}
