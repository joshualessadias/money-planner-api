package com.joshuadias.moneyplannerapi.domains.gemini.mappers;

import com.google.protobuf.Value;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcome.OutcomeRequestDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class CreateOutcomeMapper {

    private CreateOutcomeMapper() {
    }

    public static OutcomeRequestDTO toRequest(Map<String, Value> argsMap) {
        var request = new OutcomeRequestDTO();
        request.setDescription(argsMap.get("description").getStringValue());
        request.setValue(BigDecimal.valueOf(argsMap.get("value").getNumberValue()));
        request.setDate(new Date(argsMap.get("date").getStringValue()).getTime());
        request.setInstallments((int) argsMap.get("installments").getNumberValue());
        return request;
    }
}
