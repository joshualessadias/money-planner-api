package com.joshuadias.moneyplannerapi.domains.core.mappers;

import com.joshuadias.moneyplannerapi.domains.core.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.domains.core.models.Outcome;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.List;

public class OutcomeMapper {

    private static final Converter<List<Outcome>, Integer> convertChildrenInstallmentsToAmount = new AbstractConverter<>() {
        @Override
        protected Integer convert(List<Outcome> childrenInstallments) {
            if (childrenInstallments == null) {
                return 0;
            }
            return childrenInstallments.size();
        }
    };

    public static PropertyMap<Outcome, OutcomeResponseDTO> mapEntityToResponseDTO = new PropertyMap<>() {
        @Override
        protected void configure() {
            using(convertChildrenInstallmentsToAmount).map(
                    source.getChildrenInstallments(),
                    destination.getChildrenInstallmentsAmount()
            );
        }
    };
}
