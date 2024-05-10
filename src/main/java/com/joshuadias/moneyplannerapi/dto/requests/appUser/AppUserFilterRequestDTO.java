package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import com.joshuadias.moneyplannerapi.base.BaseFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserFilterRequestDTO extends BaseFilter {
    private String name;
    private String email;
}
