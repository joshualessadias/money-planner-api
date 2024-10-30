package com.joshuadias.moneyplannerapi.domains.evolution.services;

import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.services.AppUserService;
import com.joshuadias.moneyplannerapi.domains.evolution.dtos.WebhookMessagesUpsertDTO;
import com.joshuadias.moneyplannerapi.domains.evolution.helpers.ParticipantHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvolutionWebhookService {

    private final AppUserService appUserService;

    private void checkIfMessageIsMine(String phoneNumber) {
        if (!phoneNumber.equals("554498770625"))
            throw new AccessDeniedException(MessageEnum.USER_NOT_ALLOWED_WITH_PHONE_NUMBER.getMessage(phoneNumber));
    }

    public void receiveNewMessage(WebhookMessagesUpsertDTO request) {
        // TODO: implement the following method
//        var appUser = appUserService
//                .findByPhoneNumber(ParticipantHelper.getPhoneNumber(request.getData().getKey().getParticipant()));
        var senderPhoneNumber = ParticipantHelper.getPhoneNumber(request.getData().getKey().getParticipant());
        checkIfMessageIsMine(senderPhoneNumber);
        log.info("New message received from participant: {}", senderPhoneNumber);
    }
}
