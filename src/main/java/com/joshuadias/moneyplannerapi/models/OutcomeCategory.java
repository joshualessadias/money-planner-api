package com.joshuadias.moneyplannerapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// Rent, Groceries, Utilities, etc.
@Getter
@Setter
@Entity
@Table(name = "outcome_category")
public class OutcomeCategory extends BaseModel {
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;
}
