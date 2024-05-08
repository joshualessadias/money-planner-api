package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDTO implements AppUserRequestValidationConstants {

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
