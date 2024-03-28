package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.OutcomeCategory;
import com.joshuadias.moneyplannerapi.repositories.OutcomeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutcomeCategoryService
        extends AbstractServiceRepository<OutcomeCategoryRepository, OutcomeCategory, Long> {

    public OutcomeCategory findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                MessageEnum.OUTCOME_CATEGORY_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }
}
