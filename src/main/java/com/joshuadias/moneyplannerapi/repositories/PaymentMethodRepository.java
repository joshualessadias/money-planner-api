package com.joshuadias.moneyplannerapi.repositories;

import com.joshuadias.moneyplannerapi.models.PaymentMethod;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends GenericRepository<PaymentMethod, Long> {
}
