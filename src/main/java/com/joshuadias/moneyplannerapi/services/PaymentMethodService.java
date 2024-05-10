package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.PaymentMethod;
import com.joshuadias.moneyplannerapi.repositories.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService extends AbstractServiceRepository<PaymentMethodRepository, PaymentMethod, Long> {

    public PaymentMethod findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                MessageEnum.PAYMENT_METHOD_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }
}
