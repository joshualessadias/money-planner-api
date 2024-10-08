package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.base.GenericRepository;
import com.joshuadias.moneyplannerapi.models.OutcomeCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeCategoryRepository extends GenericRepository<OutcomeCategory, Long> {
    Boolean existsByName(String name);
}
