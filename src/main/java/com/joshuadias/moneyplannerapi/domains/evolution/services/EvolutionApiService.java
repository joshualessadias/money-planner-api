package com.joshuadias.moneyplannerapi.domains.evolution.services;

import com.google.gson.Gson;
import com.joshuadias.moneyplannerapi.domains.evolution.mappers.EvolutionApiDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class EvolutionApiService {

    private final RestTemplate restTemplate;
    private final String instance;

    public EvolutionApiService(Environment env, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri(env.getProperty("evolution.url"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("apiKey", env.getProperty("evolution.api-key"))
                .build();
        this.instance = env.getProperty("evolution.instance");
    }

    public void sendMessage(String chat, String message) {
        chat = "120363350761994991@g.us";
        log.info("Sending message to chat: {}", chat);
        var request = EvolutionApiDtoMapper.toSendTextDTO(chat, message);
        var json = new Gson().toJson(request);
        restTemplate.postForEntity(
                "/message/sendText/" + instance,
                json,
                Object.class
        );
    }
}
