package com.joshuadias.moneyplannerapi.domains.core.services;

import com.joshuadias.moneyplannerapi.domains.core.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.bank.BankFilterRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.bank.BankRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.responses.BankResponseDTO;
import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.domains.core.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.domains.core.models.Bank;
import com.joshuadias.moneyplannerapi.domains.core.repositories.BankRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BankService extends AbstractServiceRepository<BankRepository, Bank, Long> {

    public Bank findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException(MessageEnum.BANK_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }

    private void validateRequest(BankRequestDTO request) {
        if (repository.existsByName(request.getName())) {
            throw new BadRequestException(MessageEnum.BANK_ALREADY_EXISTS_WITH_NAME.getMessage(
                    request.getName()));
        }
        if (repository.existsByCode(request.getCode())) {
            throw new BadRequestException(MessageEnum.BANK_ALREADY_EXISTS_WITH_CODE.getMessage(
                    request.getCode()));
        }
    }

    private Bank buildEntityFromRequest(BankRequestDTO request) {
        return convertToSingleDTO(request, Bank.class);
    }

    @Transactional
    public BankResponseDTO create(BankRequestDTO request) {
        log.info(MessageEnum.BANK_CREATING.getMessage());
        validateRequest(request);
        var entity = buildEntityFromRequest(request);
        var savedEntity = save(entity);
        log.info(MessageEnum.BANK_CREATED_WITH_ID.getMessage(String.valueOf(savedEntity.getId())));
        return convertToSingleDTO(entity, BankResponseDTO.class);
    }

    public BankResponseDTO getById(Long id) {
        log.info(MessageEnum.BANK_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var entity = findByIdOrThrow(id);
        return convertToSingleDTO(entity, BankResponseDTO.class);
    }

    public List<BankResponseDTO> getAll(String orderBy) {
        log.info(MessageEnum.BANK_FINDING_ALL.getMessage());
        var sort = getSorting(orderBy);
        var entities = repository.findAll(sort);
        return convertToListDTO(entities, BankResponseDTO.class);
    }

    private void addPredicates(
            BankFilterRequestDTO filter,
            ArrayList<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Root<Bank> from
    ) {
        if (filter.getName() != null) {
            predicates.add(criteriaBuilder.like(from.get("name"), "%" + filter.getName() + "%"));
        }
        if (filter.getCode() != null) {
            predicates.add(criteriaBuilder.like(from.get("code"), "%" + filter.getCode() + "%"));
        }
    }

    private Specification<Bank> generateSpecification(BankFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(filter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<BankResponseDTO> getAllPageable(BankFilterRequestDTO filter) {
        log.info(MessageEnum.BANK_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(filter.getOrderBy());
        var pageable = generatePageable(filter.getPage(), filter.getSize(), sort);
        var spec = generateSpecification(filter);
        var pageEntities = repository.findAll(spec, pageable);
        log.info(MessageEnum.BANK_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageEntities.getNumberOfElements())));
        return convertToPageDTO(pageEntities, BankResponseDTO.class);
    }

    public BankResponseDTO update(Long id, BankRequestDTO request) {
        log.info(MessageEnum.BANK_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldEntity = findByIdOrThrow(id);
        var updatedEntityWithoutId = buildEntityFromRequest(request);
        updatedEntityWithoutId.setId(oldEntity.getId());
        var updatedEntity = save(oldEntity);
        log.info(MessageEnum.BANK_UPDATED_WITH_ID.getMessage(String.valueOf(updatedEntity.getId())));
        return convertToSingleDTO(updatedEntity, BankResponseDTO.class);
    }

    private void handleOutcomesDetach(Bank entity) {
        entity.getOutcomes().forEach(outcome -> outcome.setCategory(null));
    }

    public void delete(Long id) {
        log.info(MessageEnum.BANK_DELETING_WITH_ID.getMessage(String.valueOf(id)));
        var entityFound = findByIdOrThrow(id);
        handleOutcomesDetach(entityFound);
        repository.delete(entityFound);
        log.info(MessageEnum.BANK_DELETED_WITH_ID.getMessage(String.valueOf(id)));
    }
}
