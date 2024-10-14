package com.joshuadias.moneyplannerapi.domains.core.models;

import com.joshuadias.moneyplannerapi.domains.core.base.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// Credit Card, Debit Card, Cash, etc.
@Entity
@Table(name = "payment_method")
@Setter
@Getter
public class PaymentMethod extends BaseModel {
    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Outcome> outcomes;
}
