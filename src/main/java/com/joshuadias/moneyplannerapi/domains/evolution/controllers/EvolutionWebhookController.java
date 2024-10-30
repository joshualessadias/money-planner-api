package com.joshuadias.moneyplannerapi.domains.evolution.controllers;

import com.joshuadias.moneyplannerapi.domains.evolution.dtos.WebhookMessagesUpsertDTO;
import com.joshuadias.moneyplannerapi.domains.evolution.services.EvolutionWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evolution-webhook")
@RequiredArgsConstructor
public class EvolutionWebhookController {
    private final EvolutionWebhookService service;

    @PostMapping("/messages-upsert")
    public ResponseEntity<Void> receiveNewMessage(
            @RequestBody
            WebhookMessagesUpsertDTO request
    ) {
        service.receiveNewMessage(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
