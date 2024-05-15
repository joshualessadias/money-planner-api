package com.joshuadias.moneyplannerapi.dto.requests.outcome;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutcomeRequestDTO {
    static final String INSTALLMENTS_POSITIVE_MESSAGE = "Installments must be greater than zero";
    static final int DESCRIPTION_MAX_SIZE = 100;
    static final String DESCRIPTION_MAX_SIZE_MESSAGE = "Description must have a maximum of " + DESCRIPTION_MAX_SIZE + " characters";
    static final String DESCRIPTION_REQUIRED_MESSAGE = "Description is required";
    static final String VALUE_REQUIRED_MESSAGE = "Value is required";
    static final String VALUE_POSITIVE_MESSAGE = "Value must be positive or zero";
    static final String DATE_REQUIRED_MESSAGE = "Date is required";
    @NotBlank(message = DESCRIPTION_REQUIRED_MESSAGE)
    @Size(max = DESCRIPTION_MAX_SIZE, message = DESCRIPTION_MAX_SIZE_MESSAGE)
    private String description;

    @NotNull(message = VALUE_REQUIRED_MESSAGE)
    @PositiveOrZero(message = VALUE_POSITIVE_MESSAGE)
    private BigDecimal value;

    @NotNull(message = DATE_REQUIRED_MESSAGE)
    private Long date;


    private Long categoryId;

    private Long paymentMethodId;

    private Long bankId;

    @Positive(message = INSTALLMENTS_POSITIVE_MESSAGE)
    private Integer installments;
}
