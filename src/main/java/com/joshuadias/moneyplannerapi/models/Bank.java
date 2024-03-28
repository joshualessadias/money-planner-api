package com.joshuadias.moneyplannerapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank")
public class Bank extends BaseModel {
    @Column(name = "name", nullable = false)
    private String name;
}
