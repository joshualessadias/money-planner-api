package com.joshuadias.moneyplannerapi.domains.core.models;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bank")
@Getter
@Setter
public class Bank extends BaseModel {
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "bank")
    private List<Outcome> outcomes;
}
