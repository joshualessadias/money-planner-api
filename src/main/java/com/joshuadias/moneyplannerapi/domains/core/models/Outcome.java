package com.joshuadias.moneyplannerapi.domains.core.models;

import com.joshuadias.moneyplannerapi.domains.shared.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @ManyToOne()
    @JoinColumn(name = "installment_parent_id")
    // Should it be renamed to parentInstallment?
    private Outcome installmentParent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "installmentParent", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Outcome> childrenInstallments;
}
