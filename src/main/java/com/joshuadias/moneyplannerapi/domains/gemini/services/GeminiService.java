package com.joshuadias.moneyplannerapi.domains.gemini.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    public String sendMessage(String message) {
        log.info("Sending message to gemini: {}", message);
        return "Gemini result";
    }
}
