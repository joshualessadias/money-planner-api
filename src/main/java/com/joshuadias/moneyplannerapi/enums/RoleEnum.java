package com.joshuadias.moneyplannerapi.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    USER(1L),
    ADMIN(2L);

    private final Long id;
}
