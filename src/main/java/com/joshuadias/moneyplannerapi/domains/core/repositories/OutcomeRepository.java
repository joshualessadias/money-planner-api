package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.Outcome;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeRepository extends GenericRepository<Outcome, Long> {

    List<Outcome> findByInstallmentParentId(Long installmentParentId);
}
