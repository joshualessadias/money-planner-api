package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.OutcomeCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeCategoryRepository extends GenericRepository<OutcomeCategory, Long> {
    Boolean existsByName(String name);
}
