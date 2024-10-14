package com.joshuadias.moneyplannerapi.domains.core.repositories;

import com.joshuadias.moneyplannerapi.domains.shared.base.GenericRepository;
import com.joshuadias.moneyplannerapi.domains.core.models.PaymentMethod;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends GenericRepository<PaymentMethod, Long> {
    Boolean existsByName(String name);

    Boolean existsByCode(String code);
}
