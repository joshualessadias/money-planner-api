package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.base.GenericRepository;
import com.joshuadias.moneyplannerapi.models.SpendingGoal;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingGoalRepository extends GenericRepository<SpendingGoal, Long> {
}
