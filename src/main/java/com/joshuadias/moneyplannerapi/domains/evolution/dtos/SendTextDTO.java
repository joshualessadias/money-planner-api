package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SendTextDTO extends MetadataDTO {
    private String text;
}
