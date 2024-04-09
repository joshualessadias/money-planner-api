package com.joshuadias.moneyplannerapi.dto.requests.outcome;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OutcomeCategoryFilterRequestDTO {
    private Integer page;
    private Integer size;
    private String orderBy;
    private String name;
    private String description;
}
