package com.joshuadias.moneyplannerapi.domains.evolution.services;

import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.services.AppUserService;
import com.joshuadias.moneyplannerapi.domains.evolution.dtos.WebhookMessagesUpsertDTO;
import com.joshuadias.moneyplannerapi.domains.evolution.helpers.ParticipantHelper;
import com.joshuadias.moneyplannerapi.domains.gemini.services.GeminiService;
import com.joshuadias.moneyplannerapi.domains.shared.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvolutionWebhookService {

    private final AppUserService appUserService;
    private final GeminiService geminiService;
    private final EvolutionApiService evolutionApiService;

    private void checkIfChatIsMock(String chat) {
        if (!"120363338977336062@g.us".equals(chat))
            throw new AccessDeniedException(MessageEnum.USER_NOT_ALLOWED_WITH_PHONE_NUMBER.getMessage(chat));
    }

    private void checkIfMessageIsMine(String phoneNumber) {
        if (!"554498770625".equals(phoneNumber))
            throw new AccessDeniedException(MessageEnum.USER_NOT_ALLOWED_WITH_PHONE_NUMBER.getMessage(phoneNumber));
    }

    private void ignoreIfEmpty(String chat, String message) {
        if (message == null) {
            evolutionApiService.sendMessage(chat, MessageEnum.ANSWER_MESSAGE_EMPTY_MESSAGE_IGNORED.getMessage());
            throw new BadRequestException(MessageEnum.WEBHOOK_IGNORE_EMPTY_MESSAGE.getMessage());
        }
    }

    public void receiveNewMessage(WebhookMessagesUpsertDTO request) {
        var senderPhoneNumber = ParticipantHelper.getPhoneNumber(request.getData().getKey().getParticipant());
        var chat = request.getData().getKey().getRemoteJid();
        var message = request.getData().getMessage().getConversation();

        // TODO: implement the following method
//        var appUser = appUserService
//                .findByPhoneNumber(ParticipantHelper.getPhoneNumber(request.getData().getKey().getParticipant()));

        checkIfChatIsMock(chat);
        checkIfMessageIsMine(senderPhoneNumber);
        log.info("New message received from participant: {}", senderPhoneNumber);

        ignoreIfEmpty(chat, message);
        var geminiResult = geminiService.sendMessage(message);
        evolutionApiService.sendMessage(chat, geminiResult);
    }
}
