package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.Data;

@Data
public class MessageDTO {
    private String conversation;
    private SenderKeyDistributionMessageDTO senderKeyDistributionMessage;
    private MessageContextInfoDTO messageContextInfo;
}
