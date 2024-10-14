package com.joshuadias.moneyplannerapi.domains.core.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    private String token;
}
