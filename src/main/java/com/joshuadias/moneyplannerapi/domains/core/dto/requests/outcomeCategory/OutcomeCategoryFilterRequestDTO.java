package com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcomeCategory;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OutcomeCategoryFilterRequestDTO extends BaseFilter {
    private String name;
    private String description;
}
