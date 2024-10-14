package com.joshuadias.moneyplannerapi.domains.core.dto.responses.outcomeKpi;

import com.joshuadias.moneyplannerapi.domains.core.dto.responses.OutcomeCategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OutcomeKpiByCategoryResponseDTO {
    BigDecimal value;
    OutcomeCategoryResponseDTO category;
}
