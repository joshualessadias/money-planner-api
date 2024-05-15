package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.base.GenericRepository;
import com.joshuadias.moneyplannerapi.models.Outcome;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends GenericRepository<Outcome, Long> {

    List<Outcome> findByInstallmentParentId(Long installmentParentId);
}
