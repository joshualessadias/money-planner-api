package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.core.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.SpendingGoal;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingGoalRepository extends GenericRepository<SpendingGoal, Long> {
}