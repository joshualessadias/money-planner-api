package com.joshuadias.moneyplannerapi.dto.requests.bank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BankRequestDTO {

    static final int NAME_MAX_SIZE = 100;
    static final String NAME_MAX_SIZE_MESSAGE = "Name must have a maximum of " + NAME_MAX_SIZE + " characters";
    static final String NAME_IS_REQUIRED = "Name is required";

    static final String CODE_IS_REQUIRED = "Code is required";
    private static final int CODE_MAX_SIZE = 10;
    static final String CODE_MAX_SIZE_MESSAGE = "Code must have a maximum of " + CODE_MAX_SIZE + " characters";

    @NotBlank(message = NAME_IS_REQUIRED)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MESSAGE)
    private String name;

    @NotBlank(message = CODE_IS_REQUIRED)
    @Size(max = CODE_MAX_SIZE, message = CODE_MAX_SIZE_MESSAGE)
    private String code;
}
