package com.joshuadias.moneyplannerapi.domains.gemini.services;

import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.gemini.models.ContextMessage;
import com.joshuadias.moneyplannerapi.domains.gemini.repositories.ContextMessageRepository;
import com.joshuadias.moneyplannerapi.domains.shared.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.domains.shared.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ContextMessageService extends AbstractServiceRepository<ContextMessageRepository, ContextMessage, Long> {

    public ContextMessage findLast() {
        return repository.findByOrderByIdDesc()
                .orElseThrow(() -> new BadRequestException(MessageEnum.CONTEXT_MESSAGE_MUST_CREATE.getMessage()));
    }
}
