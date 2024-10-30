package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.Data;

@Data
public class SenderKeyDistributionMessageDTO {
    private String groupId;
    private String axolotlSenderKeyDistributionMessage;
}
