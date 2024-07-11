package com.joshuadias.moneyplannerapi.dto.responses.outcomeKpi;

import com.joshuadias.moneyplannerapi.dto.responses.OutcomeCategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OutcomeKpiByCategoryResponseDTO {
    BigDecimal value;
    OutcomeCategoryResponseDTO category;
}
