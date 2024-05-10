package com.joshuadias.moneyplannerapi.dto.responses;

import lombok.Data;

@Data
public class PaymentMethodResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
}
