package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.Data;

@Data
public class DataDTO {
    private KeyDTO key;
    private String pushName;
    private String status;
    private MessageDTO message;
    private String messageType;
    private long messageTimestamp;
    private String instanceId;
    private String source;
}
