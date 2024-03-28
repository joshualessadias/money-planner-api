package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.dto.requests.OutcomeFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.OutcomeRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.Outcome;
import com.joshuadias.moneyplannerapi.repositories.OutcomeRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutcomeService extends AbstractServiceRepository<OutcomeRepository, Outcome, Long> {
    private final OutcomeCategoryService categoryService;
    private final PaymentMethodService paymentMethodService;
    private final BankService bankService;

    private void setOutcomeParametersFromRequest(OutcomeRequestDTO outcomeRequestDto, Outcome outcome) {
        if (outcomeRequestDto.getCategoryId() != null) {
            var category = categoryService.findByIdOrThrow(outcomeRequestDto.getCategoryId());
            outcome.setCategory(category);
        }
        if (outcomeRequestDto.getPaymentMethodId() != null) {
            var paymentMethod = paymentMethodService.findByIdOrThrow(outcomeRequestDto.getPaymentMethodId());
            outcome.setPaymentMethod(paymentMethod);
        }
        if (outcomeRequestDto.getBankId() != null) {
            var bank = bankService.findByIdOrThrow(outcomeRequestDto.getBankId());
            outcome.setBank(bank);
        }
        outcome.setDescription(outcomeRequestDto.getDescription());
        outcome.setValue(outcomeRequestDto.getValue());
        outcome.setDate(new Date(outcomeRequestDto.getDate()));
    }

    private Outcome buildOutcomeFromRequest(OutcomeRequestDTO outcomeRequestDto) {
        var outcome = new Outcome();
        setOutcomeParametersFromRequest(outcomeRequestDto, outcome);
        return outcome;
    }

    @Transactional
    public void create(OutcomeRequestDTO outcomeRequestDto) {
        log.info(MessageEnum.OUTCOME_CREATING.getMessage());
        var outcome = buildOutcomeFromRequest(outcomeRequestDto);
        var createdOutcome = save(outcome);
        log.info(MessageEnum.OUTCOME_CREATED_WITH_ID.getMessage(String.valueOf(createdOutcome.getId())));
    }

    private Outcome findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageEnum.OUTCOME_NOT_FOUND_WITH_ID.getMessage(String.valueOf(
                        id))));
    }

    @Transactional
    public void update(Long id, OutcomeRequestDTO outcomeRequestDTO) {
        log.info(MessageEnum.OUTCOME_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldOutcome = findByIdOrThrow(id);
        setOutcomeParametersFromRequest(outcomeRequestDTO, oldOutcome);
        var updatedOutcome = save(oldOutcome);
        log.info(MessageEnum.OUTCOME_UPDATED_WITH_ID.getMessage(String.valueOf(updatedOutcome.getId())));
    }

    @Transactional
    public OutcomeResponseDTO getById(Long id) {
        log.info(MessageEnum.OUTCOME_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var outcome = findByIdOrThrow(id);
        return convertToSingleDTO(outcome, OutcomeResponseDTO.class);
    }

    private void addPredicates(
            OutcomeFilterRequestDTO outcomeFilter,
            ArrayList<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Root<Outcome> from
    ) {
        if (outcomeFilter.getInitialDate() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("date"), outcomeFilter.getInitialDate()));
        if (outcomeFilter.getFinalDate() != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("date"), outcomeFilter.getFinalDate()));
        if (outcomeFilter.getInitialValue() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("value"), outcomeFilter.getInitialValue()));
        if (outcomeFilter.getFinalValue() != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("value"), outcomeFilter.getFinalValue()));
        if (outcomeFilter.getDescription() != null)
            predicates.add(criteriaBuilder.like(from.get("description"), "%" + outcomeFilter.getDescription() + "%"));
        if (outcomeFilter.getCategoryId() != null)
            predicates.add(criteriaBuilder.equal(from.get("category").get("id"), outcomeFilter.getCategoryId()));
        if (outcomeFilter.getPaymentMethodId() != null)
            predicates.add(criteriaBuilder.equal(
                    from.get("paymentMethod").get("id"),
                    outcomeFilter.getPaymentMethodId()
            ));
        if (outcomeFilter.getBankId() != null)
            predicates.add(criteriaBuilder.equal(from.get("bank").get("id"), outcomeFilter.getBankId()));
    }

    private Specification<Outcome> generateSpecification(OutcomeFilterRequestDTO outcomeFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(outcomeFilter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<OutcomeResponseDTO> getAllPageable(OutcomeFilterRequestDTO outcomeFilter) {
        log.info(MessageEnum.OUTCOME_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(outcomeFilter.getOrderBy());
        var pageable = generatePageable(outcomeFilter.getPage(), outcomeFilter.getSize(), sort);
        var spec = generateSpecification(outcomeFilter);
        var pageOutcomes = repository.findAll(spec, pageable);
        log.info(MessageEnum.OUTCOME_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageOutcomes.getNumberOfElements())));
        return convertToPageDTO(pageOutcomes, OutcomeResponseDTO.class);
    }
}
