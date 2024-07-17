package com.joshuadias.moneyplannerapi.dto.requests.spendingGoal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpendingGoalRequestDTO {

    static final String VALUE_NOT_NULL_MESSAGE = "Value is required";
    static final String VALUE_POSITIVE_OR_ZERO_MESSAGE = "Value must be positive or zero";
    static final String INITIAL_DATE_NOT_NULL_MESSAGE = "Initial date is required";
    static final String FINAL_DATE_NOT_NULL_MESSAGE = "Final date is required";

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
