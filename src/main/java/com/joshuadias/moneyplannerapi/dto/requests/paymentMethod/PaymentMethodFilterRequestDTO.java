package com.joshuadias.moneyplannerapi.dto.requests.paymentMethod;

import com.joshuadias.moneyplannerapi.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentMethodFilterRequestDTO extends BaseFilter {
    private String name;
    private String description;
    private String code;
}
