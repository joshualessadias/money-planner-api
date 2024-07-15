package com.joshuadias.moneyplannerapi.models;

import com.joshuadias.moneyplannerapi.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "category_spending_goal")
public class CategorySpendingGoal extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "spending_goal_id", nullable = false)
    private SpendingGoal spendingGoal;

    @Column(name = "value", nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "outcome_category_id", nullable = false)
    private OutcomeCategory outcomeCategory;

    @Column(name = "is_percentual", nullable = false)
    private Boolean isPercentual;
}
