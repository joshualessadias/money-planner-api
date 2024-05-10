package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.dto.requests.paymentMethod.PaymentMethodRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.PaymentMethodResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.PaymentMethod;
import com.joshuadias.moneyplannerapi.repositories.PaymentMethodRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentMethodService extends AbstractServiceRepository<PaymentMethodRepository, PaymentMethod, Long> {

    public PaymentMethod findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                MessageEnum.PAYMENT_METHOD_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }

    private void validateRequest(PaymentMethodRequestDTO paymentMethodRequestDto) {
        if (repository.existsByName(paymentMethodRequestDto.getName())) {
            throw new BadRequestException(MessageEnum.PAYMENT_METHOD_ALREADY_EXISTS_WITH_NAME.getMessage(
                    paymentMethodRequestDto.getName()));
        }
        if (repository.existsByCode(paymentMethodRequestDto.getCode())) {
            throw new BadRequestException(MessageEnum.PAYMENT_METHOD_ALREADY_EXISTS_WITH_CODE.getMessage(
                    paymentMethodRequestDto.getCode()));
        }
    }

    private PaymentMethod buildEntityFromRequest(PaymentMethodRequestDTO request) {
        return convertToSingleDTO(request, PaymentMethod.class);
    }

    @Transactional
    public PaymentMethodResponseDTO create(PaymentMethodRequestDTO request) {
        log.info(MessageEnum.PAYMENT_METHOD_CREATING.getMessage());
        validateRequest(request);
        var entity = buildEntityFromRequest(request);
        var savedEntity = save(entity);
        log.info(MessageEnum.PAYMENT_METHOD_CREATED_WITH_ID.getMessage(String.valueOf(savedEntity.getId())));
        return convertToSingleDTO(entity, PaymentMethodResponseDTO.class);
    }
}
