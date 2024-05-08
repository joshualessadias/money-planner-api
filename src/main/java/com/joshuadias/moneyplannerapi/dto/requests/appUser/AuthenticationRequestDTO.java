package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequestDTO implements AppUserRequestValidationConstants {

    @NotBlank(message = EMAIL_NOT_BLANK_MESSAGE)
    @Size(max = EMAIL_MAX_SIZE, message = EMAIL_MAX_SIZE_MESSAGE)
    @Email(message = EMAIL_MUST_BE_VALID)
    private String email;

    @NotBlank(message = PASSWORD_NOT_BLANK_MESSAGE)
    @Size(max = PASSWORD_MAX_SIZE, message = PASSWORD_MAX_SIZE_MESSAGE)
    private String password;
}
