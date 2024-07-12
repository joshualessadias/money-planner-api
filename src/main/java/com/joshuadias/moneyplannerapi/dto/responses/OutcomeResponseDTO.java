package com.joshuadias.moneyplannerapi.dto.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OutcomeResponseDTO {
    private Long id;
    private String description;
    private BigDecimal value;
    private Long date;
    private OutcomeCategoryResponseDTO category;
    private PaymentMethodResponseDTO paymentMethod;
    private BankResponseDTO bank;
    private Integer childrenInstallmentsAmount;
}
