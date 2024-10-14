package com.joshuadias.moneyplannerapi.domains.core.dto.responses.spendingGoal;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpendingGoalResponseDTO {

    private Long id;
    private String name;
    private BigDecimal value;
    private Long initialDate;
    private Long finalDate;
    private List<CategorySpendingGoalResponseDTO> categorySpendingGoalList;
}
