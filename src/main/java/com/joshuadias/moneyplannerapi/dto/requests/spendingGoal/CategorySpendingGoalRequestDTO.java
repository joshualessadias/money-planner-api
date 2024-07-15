package com.joshuadias.moneyplannerapi.dto.requests.spendingGoal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategorySpendingGoalRequestDTO {

    static final String VALUE_NOT_NULL_MESSAGE = "Value is required";
    static final String VALUE_POSITIVE_OR_ZERO_MESSAGE = "Value must be positive or zero";
    static final String OUTCOME_CATEGORY_ID_NOT_NULL_MESSAGE = "Outcome category id is required";

    @NotNull(message = VALUE_NOT_NULL_MESSAGE)
    @PositiveOrZero(message = VALUE_POSITIVE_OR_ZERO_MESSAGE)
    private BigDecimal value;

    @NotNull(message = OUTCOME_CATEGORY_ID_NOT_NULL_MESSAGE)
    private Long outcomeCategoryId;

    private Boolean isPercentual;
}
