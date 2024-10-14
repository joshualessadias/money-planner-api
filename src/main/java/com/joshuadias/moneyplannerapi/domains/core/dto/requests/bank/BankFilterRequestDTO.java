package com.joshuadias.moneyplannerapi.domains.core.dto.requests.bank;

import com.joshuadias.moneyplannerapi.domains.core.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankFilterRequestDTO extends BaseFilter {
    private String name;
    private String code;
}
