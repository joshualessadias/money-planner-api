package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleRequestDTO {
    public static final int NAME_MAX_SIZE = 50;
    public static final String NAME_NOT_BLANK_MESSAGE = "Name is required";
    public static final String NAME_MAX_SIZE_MESSAGE = "Name must be less than " + NAME_MAX_SIZE + " characters";

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MESSAGE)
    private String name;
}
