package com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcome;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class OutcomeFilterRequestDTO extends BaseFilter {
    private Long categoryId;
    private Long paymentMethodId;
    private Long bankId;
    private Date initialDate;
    private Date finalDate;
    private BigDecimal initialValue;
    private BigDecimal finalValue;
    private String description;
    private Boolean findAll;
    private Boolean hideInstallments;
}
