package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUserRequestDTO {
    public static final int FIRST_NAME_MAX_SIZE = 50;
    public static final int LAST_NAME_MAX_SIZE = 50;
    public static final int EMAIL_MAX_SIZE = 50;
    public static final int PASSWORD_MAX_SIZE = 50;
    public static final String FIRST_NAME_NOT_BLANK_MESSAGE = "First name is required";
    public static final String LAST_NAME_NOT_BLANK_MESSAGE = "Last name is required";
    public static final String EMAIL_NOT_BLANK_MESSAGE = "Email is required";
    public static final String PASSWORD_NOT_BLANK_MESSAGE = "Password is required";
    public static final String FIRST_NAME_MAX_SIZE_MESSAGE = "First name must have a maximum of " + FIRST_NAME_MAX_SIZE + " characters";
    public static final String LAST_NAME_MAX_SIZE_MESSAGE = "Last name must have a maximum of " + LAST_NAME_MAX_SIZE + " characters";
    public static final String EMAIL_MAX_SIZE_MESSAGE = "Email must have a maximum of " + EMAIL_MAX_SIZE + " characters";
    public static final String PASSWORD_MAX_SIZE_MESSAGE = "Password must have a maximum of " + PASSWORD_MAX_SIZE + " characters";
    public static final String EMAIL_MUST_BE_VALID = "Email must be valid";

    @NotBlank(message = FIRST_NAME_NOT_BLANK_MESSAGE)
    @Size(max = FIRST_NAME_MAX_SIZE, message = FIRST_NAME_MAX_SIZE_MESSAGE)
    private String firstName;

    @NotBlank(message = LAST_NAME_NOT_BLANK_MESSAGE)
    @Size(max = LAST_NAME_MAX_SIZE, message = LAST_NAME_MAX_SIZE_MESSAGE)
    private String lastName;

    @NotBlank(message = EMAIL_NOT_BLANK_MESSAGE)
    @Size(max = EMAIL_MAX_SIZE, message = EMAIL_MAX_SIZE_MESSAGE)
    @Email(message = EMAIL_MUST_BE_VALID)
    private String email;

    @NotBlank(message = PASSWORD_NOT_BLANK_MESSAGE)
    @Size(max = PASSWORD_MAX_SIZE, message = PASSWORD_MAX_SIZE_MESSAGE)
    private String password;
}
