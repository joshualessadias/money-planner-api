package com.joshuadias.moneyplannerapi.dto.responses;

import com.joshuadias.moneyplannerapi.enums.RoleEnum;
import lombok.Data;

import java.util.List;

@Data
public class AppUserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<RoleEnum> roles;
}
