package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppUserRequestDTO {
    public static final int NAME_MAX_SIZE = 50;
    public static final int EMAIL_MAX_SIZE = 50;
    public static final int PASSWORD_MAX_SIZE = 50;
    public static final String NAME_NOT_BLANK_MESSAGE = "Name is required";
    public static final String EMAIL_NOT_BLANK_MESSAGE = "Email is required";
    public static final String PASSWORD_NOT_BLANK_MESSAGE = "Password is required";
    public static final String NAME_MAX_SIZE_MESSAGE = "Name must have a maximum of " + NAME_MAX_SIZE + " characters";
    public static final String EMAIL_MAX_SIZE_MESSAGE = "Email must have a maximum of " + EMAIL_MAX_SIZE + " characters";
    public static final String PASSWORD_MAX_SIZE_MESSAGE = "Password must have a maximum of " + PASSWORD_MAX_SIZE + " characters";
    public static final String EMAIL_MUST_BE_VALID = "Email must be valid";

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MESSAGE)
    private String name;
    @NotBlank(message = EMAIL_NOT_BLANK_MESSAGE)
    @Size(max = EMAIL_MAX_SIZE, message = EMAIL_MAX_SIZE_MESSAGE)
    @Email(message = EMAIL_MUST_BE_VALID)
    private String email;
    @NotBlank(message = PASSWORD_NOT_BLANK_MESSAGE)
    @Size(max = PASSWORD_MAX_SIZE, message = PASSWORD_MAX_SIZE_MESSAGE)
    private String password;
}
