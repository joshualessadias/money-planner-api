package com.joshuadias.moneyplannerapi.domains.core.models;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Income extends BaseModel {
    private String description;
    private BigDecimal value;
    private LocalDate date;
    private Bank bank;
    private IncomeCategory category;
}
