package com.joshuadias.moneyplannerapi.domains.core.services;

import com.joshuadias.moneyplannerapi.domains.core.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcomeCategory.OutcomeCategoryFilterRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcomeCategory.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.domains.core.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.domains.core.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.domains.core.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.domains.core.models.OutcomeCategory;
import com.joshuadias.moneyplannerapi.domains.core.repositories.OutcomeCategoryRepository;
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
public class OutcomeCategoryService
        extends AbstractServiceRepository<OutcomeCategoryRepository, OutcomeCategory, Long> {

    private OutcomeCategory buildEntityFromRequest(OutcomeCategoryRequestDTO outcomeCategoryRequestDto) {
        return convertToSingleDTO(outcomeCategoryRequestDto, OutcomeCategory.class);
    }

    private void checkIfNameExists(String name) {
        if (repository.existsByName(name)) {
            throw new BadRequestException(MessageEnum.OUTCOME_CATEGORY_ALREADY_EXISTS_WITH_NAME.getMessage(name));
        }
    }

    @Transactional
    public OutcomeCategoryResponseDTO create(OutcomeCategoryRequestDTO outcomeCategoryRequestDto) {
        log.info(MessageEnum.OUTCOME_CATEGORY_CREATING.getMessage());
        checkIfNameExists(outcomeCategoryRequestDto.getName());
        var outcomeCategory = buildEntityFromRequest(outcomeCategoryRequestDto);
        var savedOutcomeCategory = save(outcomeCategory);
        log.info(MessageEnum.OUTCOME_CATEGORY_CREATED_WITH_ID.getMessage(String.valueOf(savedOutcomeCategory.getId())));
        return convertToSingleDTO(savedOutcomeCategory, OutcomeCategoryResponseDTO.class);
    }

    public OutcomeCategory findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                MessageEnum.OUTCOME_CATEGORY_NOT_FOUND_WITH_ID.getMessage(String.valueOf(id))));
    }

    public OutcomeCategoryResponseDTO getById(Long id) {
        log.info(MessageEnum.OUTCOME_CATEGORY_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var outcomeCategory = findByIdOrThrow(id);
        return convertToSingleDTO(outcomeCategory, OutcomeCategoryResponseDTO.class);
    }

    public List<OutcomeCategoryResponseDTO> getAll(String orderBy) {
        log.info(MessageEnum.OUTCOME_CATEGORY_FINDING_ALL.getMessage());
        var sort = getSorting(orderBy);
        var entities = repository.findAll(sort);
        return convertToListDTO(entities, OutcomeCategoryResponseDTO.class);
    }

    private void addPredicates(
            OutcomeCategoryFilterRequestDTO outcomeCategoryFilter,
            ArrayList<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Root<OutcomeCategory> from
    ) {
        if (outcomeCategoryFilter.getName() != null) {
            predicates.add(criteriaBuilder.like(from.get("name"), "%" + outcomeCategoryFilter.getName() + "%"));
        }
        if (outcomeCategoryFilter.getDescription() != null) {
            predicates.add(criteriaBuilder.like(
                    from.get("description"),
                    "%" + outcomeCategoryFilter.getDescription() + "%"
            ));
        }
    }

    private Specification<OutcomeCategory> generateSpecification(OutcomeCategoryFilterRequestDTO outcomeCategoryFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(outcomeCategoryFilter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<OutcomeCategoryResponseDTO> getAllPageable(OutcomeCategoryFilterRequestDTO outcomeCategoryFilter) {
        log.info(MessageEnum.OUTCOME_CATEGORY_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(outcomeCategoryFilter.getOrderBy());
        var pageable = generatePageable(outcomeCategoryFilter.getPage(), outcomeCategoryFilter.getSize(), sort);
        var spec = generateSpecification(outcomeCategoryFilter);
        var pageOutcomeCategories = repository.findAll(spec, pageable);
        log.info(MessageEnum.OUTCOME_CATEGORY_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageOutcomeCategories.getNumberOfElements())));
        return convertToPageDTO(pageOutcomeCategories, OutcomeCategoryResponseDTO.class);
    }

    @Transactional
    public OutcomeCategoryResponseDTO update(Long id, OutcomeCategoryRequestDTO request) {
        log.info(MessageEnum.OUTCOME_CATEGORY_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldEntity = findByIdOrThrow(id);
        var updatedEntityWithoutId = buildEntityFromRequest(request);
        updatedEntityWithoutId.setId(oldEntity.getId());
        var updatedEntity = save(oldEntity);
        log.info(MessageEnum.OUTCOME_CATEGORY_UPDATED_WITH_ID.getMessage(String.valueOf(updatedEntity.getId())));
        return convertToSingleDTO(updatedEntity, OutcomeCategoryResponseDTO.class);
    }

    private void handleOutcomesDetach(OutcomeCategory outcomeCategory) {
        outcomeCategory.getOutcomes().forEach(outcome -> outcome.setCategory(null));
    }

    @Transactional
    public void delete(Long id) {
        log.info(MessageEnum.OUTCOME_CATEGORY_DELETING_WITH_ID.getMessage(String.valueOf(id)));
        var outcomeCategoryFound = findByIdOrThrow(id);
        handleOutcomesDetach(outcomeCategoryFound);
        repository.delete(outcomeCategoryFound);
        log.info(MessageEnum.OUTCOME_CATEGORY_DELETED_WITH_ID.getMessage(String.valueOf(id)));
    }
}
