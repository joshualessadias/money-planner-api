package com.joshuadias.moneyplannerapi.domains.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    USER(1L),
    ADMIN(2L);

    public static final String USER_STR = "USER";
    public static final String ADMIN_STR = "ADMIN";

    private final Long id;
}
