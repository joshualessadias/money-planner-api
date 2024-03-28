package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.models.Bank;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends GenericRepository<Bank, Long> {
}
