package com.joshuadias.moneyplannerapi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Rent, Groceries, Utilities, etc.
@Getter
@Setter
@Entity
@Table(name = "outcome_category")
public class OutcomeCategory extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;
}
