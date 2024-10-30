package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.Data;

@Data
public class KeyDTO {
    private String remoteJid;
    private boolean fromMe;
    private String id;
    private String participant;
}
