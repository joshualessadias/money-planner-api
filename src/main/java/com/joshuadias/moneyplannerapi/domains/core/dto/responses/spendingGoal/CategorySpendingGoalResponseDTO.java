package com.joshuadias.moneyplannerapi.domains.core.dto.responses.spendingGoal;

import com.joshuadias.moneyplannerapi.domains.core.dto.responses.OutcomeCategoryResponseDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategorySpendingGoalResponseDTO {

    private Long id;
    private BigDecimal value;
    private Boolean isPercentual;
    private OutcomeCategoryResponseDTO outcomeCategory;
}
