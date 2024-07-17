package com.joshuadias.moneyplannerapi.models;

import com.joshuadias.moneyplannerapi.base.BaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spending_goal")
public class SpendingGoal extends BaseModel {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "value", nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @Column(name = "initial_date", nullable = false)
    private Date initialDate;

    @Column(name = "final_date", nullable = false)
    private Date finalDate;

    @OneToMany(mappedBy = "spendingGoal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategorySpendingGoal> categorySpendingGoalList;
}
