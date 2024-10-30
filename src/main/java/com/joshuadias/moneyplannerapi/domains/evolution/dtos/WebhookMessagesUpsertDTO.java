package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.Data;

@Data
public class WebhookMessagesUpsertDTO {
    private String event;
    private String instance;
    private DataDTO data;
    private String destination;
    private String dateTime;
    private String sender;
    private String serverUrl;
    private String apikey;
}