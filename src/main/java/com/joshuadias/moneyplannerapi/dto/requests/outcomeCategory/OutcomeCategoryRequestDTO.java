package com.joshuadias.moneyplannerapi.dto.requests.outcomeCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class OutcomeCategoryRequestDTO {
    static final int DESCRIPTION_MAX_SIZE = 255;
    static final String DESCRIPTION_MAX_SIZE_MESSAGE = "Description must have a maximum of " + DESCRIPTION_MAX_SIZE + " characters";
    static final int NAME_MAX_SIZE = 50;
    static final String NAME_MAX_SIZE_MESSAGE = "Name must have a maximum of " + NAME_MAX_SIZE + " characters";
    static final String NAME_IS_REQUIRED = "Name is required";

    @NotBlank(message = NAME_IS_REQUIRED)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MESSAGE)
    private String name;

    @Size(max = DESCRIPTION_MAX_SIZE, message = DESCRIPTION_MAX_SIZE_MESSAGE)
    private String description;
}
