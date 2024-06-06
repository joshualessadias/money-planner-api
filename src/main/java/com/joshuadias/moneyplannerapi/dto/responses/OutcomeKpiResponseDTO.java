package com.joshuadias.moneyplannerapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OutcomeKpiResponseDTO {
    BigDecimal totalValue;
}
