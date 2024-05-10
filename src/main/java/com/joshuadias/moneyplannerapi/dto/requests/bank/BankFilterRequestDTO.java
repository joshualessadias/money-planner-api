package com.joshuadias.moneyplannerapi.dto.requests.bank;

import com.joshuadias.moneyplannerapi.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankFilterRequestDTO extends BaseFilter {
    private String name;
    private String code;
}
