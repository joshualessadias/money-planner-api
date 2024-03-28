package com.joshuadias.moneyplannerapi.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutcomeResponseDTO {
    private Long id;
    private String description;
    private BigDecimal value;
    private String date;
    private Long categoryId;
    private Long paymentMethodId;
    private Long bankId;
}
