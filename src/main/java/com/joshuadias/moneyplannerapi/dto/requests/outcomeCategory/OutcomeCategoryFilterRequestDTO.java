package com.joshuadias.moneyplannerapi.dto.requests.outcomeCategory;

import com.joshuadias.moneyplannerapi.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutcomeCategoryFilterRequestDTO extends BaseFilter {
    private String name;
    private String description;
}
