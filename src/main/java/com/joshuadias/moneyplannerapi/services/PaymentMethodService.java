package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.dto.requests.paymentMethod.PaymentMethodFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.paymentMethod.PaymentMethodRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.PaymentMethodResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.PaymentMethod;
import com.joshuadias.moneyplannerapi.repositories.PaymentMethodRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public PaymentMethodResponseDTO getById(Long id) {
        log.info(MessageEnum.PAYMENT_METHOD_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var entity = findByIdOrThrow(id);
        return convertToSingleDTO(entity, PaymentMethodResponseDTO.class);
    }

    public List<PaymentMethodResponseDTO> getAll() {
        log.info(MessageEnum.PAYMENT_METHOD_FINDING_ALL.getMessage());
        var entities = repository.findAll();
        return convertToListDTO(entities, PaymentMethodResponseDTO.class);
    }

    private void addPredicates(
            PaymentMethodFilterRequestDTO filter,
            ArrayList<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Root<PaymentMethod> from
    ) {
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(from.get("name"), "%" + filter.getName() + "%"));
        }
        if (filter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(
                    from.get("description"),
                    "%" + filter.getDescription() + "%"
            ));
        }
        if (filter.getCode() != null) {
            predicates.add(criteriaBuilder.like(from.get("code"), "%" + filter.getCode() + "%"));
        }
    }

    private Specification<PaymentMethod> generateSpecification(PaymentMethodFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(filter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<PaymentMethodResponseDTO> getAllPageable(PaymentMethodFilterRequestDTO filter) {
        log.info(MessageEnum.PAYMENT_METHOD_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(filter.getOrderBy());
        var pageable = generatePageable(filter.getPage(), filter.getSize(), sort);
        var spec = generateSpecification(filter);
        var pageEntities = repository.findAll(spec, pageable);
        log.info(MessageEnum.PAYMENT_METHOD_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageEntities.getNumberOfElements())));
        return convertToPageDTO(pageEntities, PaymentMethodResponseDTO.class);
    }

    public PaymentMethodResponseDTO update(Long id, PaymentMethodRequestDTO request) {
        log.info(MessageEnum.PAYMENT_METHOD_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldEntity = findByIdOrThrow(id);
        var updatedEntityWithoutId = buildEntityFromRequest(request);
        updatedEntityWithoutId.setId(oldEntity.getId());
        var updatedEntity = save(oldEntity);
        log.info(MessageEnum.PAYMENT_METHOD_UPDATED_WITH_ID.getMessage(String.valueOf(updatedEntity.getId())));
        return convertToSingleDTO(updatedEntity, PaymentMethodResponseDTO.class);
    }

    private void handleOutcomesDetach(PaymentMethod entity) {
        entity.getOutcomes().forEach(outcome -> outcome.setCategory(null));
    }

    public void delete(Long id) {
        log.info(MessageEnum.PAYMENT_METHOD_DELETING_WITH_ID.getMessage(String.valueOf(id)));
        var entityFound = findByIdOrThrow(id);
        handleOutcomesDetach(entityFound);
        repository.delete(entityFound);
        log.info(MessageEnum.PAYMENT_METHOD_DELETED_WITH_ID.getMessage(String.valueOf(id)));
    }
}
