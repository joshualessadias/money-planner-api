package com.joshuadias.moneyplannerapi.domains.core.dto.requests.appUser;

import com.joshuadias.moneyplannerapi.domains.core.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserFilterRequestDTO extends BaseFilter {
    private String name;
    private String email;
}
