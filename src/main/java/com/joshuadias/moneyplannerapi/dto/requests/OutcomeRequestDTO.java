package com.joshuadias.moneyplannerapi.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutcomeRequestDTO {
    static final String DESCRIPTION_REQUIRED_MESSAGE = "Description is required";
    static final String VALUE_REQUIRED_MESSAGE = "Value is required";
    static final String VALUE_POSITIVE_MESSAGE = "Value must be positive or zero";
    static final String DATE_REQUIRED_MESSAGE = "Date is required";

    @NotBlank(message = DESCRIPTION_REQUIRED_MESSAGE)
    private String description;

    @NotNull(message = VALUE_REQUIRED_MESSAGE)
    @PositiveOrZero(message = VALUE_POSITIVE_MESSAGE)
    private BigDecimal value;

    @NotNull(message = DATE_REQUIRED_MESSAGE)
    private Long date;

    private Long categoryId;

    private Long paymentMethodId;

    private Long bankId;
}
