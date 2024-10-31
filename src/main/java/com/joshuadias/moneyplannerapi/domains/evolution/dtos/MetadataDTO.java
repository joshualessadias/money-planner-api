package com.joshuadias.moneyplannerapi.domains.evolution.dtos;

import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class MetadataDTO {
    private String number;
    private Integer delay;
    //    private Quoted quoted;
    private Boolean linkPreview;
    private Boolean mentionsEveryOne;
    private List<String> mentioned;
    private Boolean encoding;
}
