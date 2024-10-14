package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.Bank;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends GenericRepository<Bank, Long> {
    Boolean existsByName(String name);

    Boolean existsByCode(String code);
}
