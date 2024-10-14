package com.joshuadias.moneyplannerapi.domains.core.models;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// Rent, Groceries, Utilities, etc.
@Getter
@Setter
@Entity
@Table(name = "outcome_category")
public class OutcomeCategory extends BaseModel {
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Outcome> outcomes;
}
