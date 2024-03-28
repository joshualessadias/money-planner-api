package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.Bank;
import com.joshuadias.moneyplannerapi.repositories.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BankService extends AbstractServiceRepository<BankRepository, Bank, Long> {

    public Bank findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageEnum.BANK_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }
}
