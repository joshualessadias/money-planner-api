package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.base.GenericRepository;
import com.joshuadias.moneyplannerapi.models.Outcome;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepository extends GenericRepository<Outcome, Long> {

}
