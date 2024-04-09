package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeCategoryFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.OutcomeCategory;
import com.joshuadias.moneyplannerapi.repositories.OutcomeCategoryRepository;
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

    private void setOutcomeCategoryParametersFromRequest(
            OutcomeCategoryRequestDTO outcomeCategoryRequestDto,
            OutcomeCategory outcomeCategory
    ) {
        outcomeCategory.setName(outcomeCategoryRequestDto.getName());
        outcomeCategory.setDescription(outcomeCategoryRequestDto.getDescription());
    }

    private OutcomeCategory buildOutcomeCategoryFromRequest(OutcomeCategoryRequestDTO outcomeCategoryRequestDto) {
        var outcomeCategory = new OutcomeCategory();
        setOutcomeCategoryParametersFromRequest(outcomeCategoryRequestDto, outcomeCategory);
        return outcomeCategory;
    }

    private void checkIfNameExists(String name) {
        if (repository.existsByName(name)) {
            throw new BadRequestException(MessageEnum.OUTCOME_CATEGORY_ALREADY_EXISTS_WITH_NAME.getMessage(name));
        }
    }

    @Transactional
    public void create(OutcomeCategoryRequestDTO outcomeCategoryRequestDto) {
        log.info(MessageEnum.OUTCOME_CATEGORY_CREATING.getMessage());
        checkIfNameExists(outcomeCategoryRequestDto.getName());
        var outcomeCategory = buildOutcomeCategoryFromRequest(outcomeCategoryRequestDto);
        var savedOutcomeCategory = save(outcomeCategory);
        log.info(MessageEnum.OUTCOME_CATEGORY_CREATED_WITH_ID.getMessage(String.valueOf(savedOutcomeCategory.getId())));
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

    public List<OutcomeCategoryResponseDTO> getAll() {
        log.info(MessageEnum.OUTCOME_CATEGORY_FINDING_ALL.getMessage());
        var outcomeCategories = repository.findAll();
        return convertToListDTO(outcomeCategories, OutcomeCategoryResponseDTO.class);
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

    public void update(Long id, OutcomeCategoryRequestDTO outcomeCategoryRequestDTO) {
        log.info(MessageEnum.OUTCOME_CATEGORY_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldOutcomeCategory = findByIdOrThrow(id);
        setOutcomeCategoryParametersFromRequest(outcomeCategoryRequestDTO, oldOutcomeCategory);
        var updatedOutcome = save(oldOutcomeCategory);
        log.info(MessageEnum.OUTCOME_CATEGORY_UPDATED_WITH_ID.getMessage(String.valueOf(updatedOutcome.getId())));
    }
}
