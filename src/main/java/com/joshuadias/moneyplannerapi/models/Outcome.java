package com.joshuadias.moneyplannerapi.models;

import com.joshuadias.moneyplannerapi.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "outcome")
public class Outcome extends BaseModel {
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "value", nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private OutcomeCategory category;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "installment_parent_id")
    private Outcome installmentParent;
}
