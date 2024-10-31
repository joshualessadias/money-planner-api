package com.joshuadias.moneyplannerapi.domains.evolution.mappers;

import com.joshuadias.moneyplannerapi.domains.evolution.dtos.SendTextDTO;

public class EvolutionApiDtoMapper {

    private EvolutionApiDtoMapper() {
    }

    public static SendTextDTO toSendTextDTO(String chat, String message) {
        return SendTextDTO.builder()
                .text(message)
                .number(chat)
                .build();
    }
}
