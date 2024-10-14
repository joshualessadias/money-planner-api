package com.joshuadias.moneyplannerapi.domains.core.dto.responses;

import lombok.Data;

@Data
public class BankResponseDTO {
    private Long id;
    private String name;
    private String code;
}
