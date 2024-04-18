package com.joshuadias.moneyplannerapi.dto.requests.appUser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppUserFilterRequestDTO {
    private String orderBy;
    private Integer page;
    private Integer size;
    private String name;
    private String email;
}
