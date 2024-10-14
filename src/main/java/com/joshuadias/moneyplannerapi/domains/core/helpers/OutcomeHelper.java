package com.joshuadias.moneyplannerapi.domains.core.helpers;

import com.joshuadias.moneyplannerapi.domains.core.models.Outcome;

public class OutcomeHelper {

    public static void buildInstallmentDescription(
            Outcome outcome,
            Integer currentInstallment,
            Integer totalInstallments
    ) {
        outcome.setDescription(String.format(
                "%s - %d/%d",
                outcome.getDescription(),
                currentInstallment,
                totalInstallments
        ));
    }
}
