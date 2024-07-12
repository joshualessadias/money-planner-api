package com.joshuadias.moneyplannerapi.mappers;

import com.joshuadias.moneyplannerapi.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.models.Outcome;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

import java.util.List;

public class OutcomeMapper {

    private static final Converter<List<Outcome>, Integer> convertChildrenInstallmentsToAmount = new AbstractConverter<>() {
        @Override
        protected Integer convert(List<Outcome> childrenInstallments) {
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
