package com.joshuadias.moneyplannerapi.dto.requests;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
public class OutcomeFilterRequestDTO {
    private Integer page;
    private Integer size;
    private String orderBy;
    private Long categoryId;
    private Long paymentMethodId;
    private Long bankId;
    private Date initialDate;
    private Date finalDate;
    private BigDecimal initialValue;
    private BigDecimal finalValue;
    private String description;
}
