package com.joshuadias.moneyplannerapi.domains.core.dto.requests.paymentMethod;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodFilterRequestDTO extends BaseFilter {
    private String name;
    private String description;
    private String code;
}
