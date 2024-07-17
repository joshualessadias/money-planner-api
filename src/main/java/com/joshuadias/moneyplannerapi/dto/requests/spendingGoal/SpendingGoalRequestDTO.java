package com.joshuadias.moneyplannerapi.dto.requests.spendingGoal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpendingGoalRequestDTO {

    static final String NAME_REQUIRED_MESSAGE = "Name is required";
    static final int NAME_MAX_SIZE = 50;
    static final String NAME_MAX_SIZE_DESCRIPTION = "Name must be less than or equal to " + NAME_MAX_SIZE + " characters";
    static final String VALUE_NOT_NULL_MESSAGE = "Value is required";
    static final String VALUE_POSITIVE_OR_ZERO_MESSAGE = "Value must be positive or zero";
    static final String INITIAL_DATE_NOT_NULL_MESSAGE = "Initial date is required";
    static final String FINAL_DATE_NOT_NULL_MESSAGE = "Final date is required";

    @NotBlank(message = NAME_REQUIRED_MESSAGE)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_DESCRIPTION)
    private String name;

    @NotNull(message = VALUE_NOT_NULL_MESSAGE)
    @PositiveOrZero(message = VALUE_POSITIVE_OR_ZERO_MESSAGE)
    private BigDecimal value;

    @NotNull(message = INITIAL_DATE_NOT_NULL_MESSAGE)
    private Long initialDate;

    @NotNull(message = FINAL_DATE_NOT_NULL_MESSAGE)
    private Long finalDate;

    @Valid
    private List<CategorySpendingGoalRequestDTO> categorySpendingGoalList;
}
