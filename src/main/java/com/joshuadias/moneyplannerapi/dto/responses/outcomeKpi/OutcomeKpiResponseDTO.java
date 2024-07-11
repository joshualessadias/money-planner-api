package com.joshuadias.moneyplannerapi.dto.responses.outcomeKpi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Data
public class OutcomeKpiResponseDTO {
    BigDecimal totalValue;
    List<OutcomeKpiByCategoryResponseDTO> kpiByCategoryList;
}
