package com.joshuadias.moneyplannerapi.domains.shared.base;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseFilter {
    private Integer page;
    private Integer size;
    private String orderBy;
}
