package com.joshuadias.moneyplannerapi.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class AppUserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<Long> rolesId;
}
