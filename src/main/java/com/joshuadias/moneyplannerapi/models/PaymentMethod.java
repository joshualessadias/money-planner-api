package com.joshuadias.moneyplannerapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// Credit Card, Debit Card, Cash, etc.
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;
}
