package com.joshuadias.moneyplannerapi.domains.gemini.services;

import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.joshuadias.moneyplannerapi.domains.shared.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    private final GenerativeModel model;
    private final ContextMessageService contextMessageService;

    private List<Content> buildContextMessage() {
        var contextMessage = contextMessageService.findLast().getText();
        contextMessage = contextMessage + "\nImportant: Consider that today's date is " + LocalDate.now();
        return List.of(
                ContentMaker.forRole("user").fromString(contextMessage)
        );
    }

    public String sendMessage(String message) {
        log.info("Sending message to gemini: {}", message);
        GenerateContentResponse response;
        try {
            var chat = model.startChat();
            chat.setHistory(buildContextMessage());
            response = chat.sendMessage(message);
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }
        return ResponseHandler.getText(response);
    }
}
