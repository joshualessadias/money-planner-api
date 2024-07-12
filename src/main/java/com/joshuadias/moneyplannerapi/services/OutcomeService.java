package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.dto.responses.outcomeKpi.OutcomeKpiByCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.dto.responses.outcomeKpi.OutcomeKpiResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.helpers.OutcomeHelper;
import com.joshuadias.moneyplannerapi.mappers.OutcomeMapper;
import com.joshuadias.moneyplannerapi.models.Outcome;
import com.joshuadias.moneyplannerapi.repositories.OutcomeRepository;
import com.joshuadias.moneyplannerapi.utils.DateUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutcomeService extends AbstractServiceRepository<OutcomeRepository, Outcome, Long> {

    private final OutcomeCategoryService categoryService;
    private final PaymentMethodService paymentMethodService;
    private final BankService bankService;

    private void setOutcomeParametersFromRequest(OutcomeRequestDTO request, Outcome entity) {
        entity.setDescription(request.getDescription());
        entity.setValue(request.getValue());
        entity.setDate(new Date(request.getDate()));
        if (request.getCategoryId() != null) {
            var category = categoryService.findByIdOrThrow(request.getCategoryId());
            entity.setCategory(category);
        }
        if (request.getPaymentMethodId() != null) {
            var paymentMethod = paymentMethodService.findByIdOrThrow(request.getPaymentMethodId());
            entity.setPaymentMethod(paymentMethod);
        }
        if (request.getBankId() != null) {
            var bank = bankService.findByIdOrThrow(request.getBankId());
            entity.setBank(bank);
        }
    }

    private Outcome buildOutcomeFromRequest(OutcomeRequestDTO outcomeRequestDto) {
        var outcome = new Outcome();
        setOutcomeParametersFromRequest(outcomeRequestDto, outcome);
        return outcome;
    }

    private Outcome buildInstallmentOutcome(
            OutcomeRequestDTO outcomeRequestDto,
            Outcome parentOutcome,
            Integer installmentNumber
    ) {
        var outcome = buildOutcomeFromRequest(outcomeRequestDto);
        OutcomeHelper.buildInstallmentDescription(outcome, installmentNumber, outcomeRequestDto.getInstallments());
        var installmentDate = DateUtils.addMonthsToDate(outcome.getDate(), installmentNumber - 1);
        outcome.setDate(installmentDate);
        outcome.setInstallmentParent(parentOutcome);
        return outcome;
    }

    private List<Outcome> createMultipleInstallmentsOutcomes(OutcomeRequestDTO outcomeRequestDto) {
        var outcomes = new ArrayList<Outcome>();

        var parentOutcome = buildOutcomeFromRequest(outcomeRequestDto);
        OutcomeHelper.buildInstallmentDescription(parentOutcome, 1, outcomeRequestDto.getInstallments());
        var installmentDate = DateUtils.addMonthsToDate(parentOutcome.getDate(), 0);
        parentOutcome.setDate(installmentDate);
        outcomes.add(parentOutcome);

        for (int i = 2; i <= outcomeRequestDto.getInstallments(); i++) {
            var outcome = buildInstallmentOutcome(outcomeRequestDto, parentOutcome, i);
            outcomes.add(outcome);
        }
        return repository.saveAll(outcomes);
    }

    @Transactional
    public OutcomeResponseDTO create(OutcomeRequestDTO request) {
        log.info(MessageEnum.OUTCOME_CREATING.getMessage());
        if (request.getInstallments() != null && request.getInstallments() > 1) {
            var outcomes = createMultipleInstallmentsOutcomes(request);
            log.info(MessageEnum.OUTCOME_CREATED_MULTIPLE.getMessage(String.valueOf(request.getInstallments())));
            return convertToSingleDTO(
                    outcomes.getFirst(),
                    OutcomeResponseDTO.class,
                    OutcomeMapper.mapEntityToResponseDTO
            );
        } else {
            var outcome = buildOutcomeFromRequest(request);
            var createdOutcome = save(outcome);
            log.info(MessageEnum.OUTCOME_CREATED_WITH_ID.getMessage(String.valueOf(createdOutcome.getId())));
            return convertToSingleDTO(createdOutcome, OutcomeResponseDTO.class, OutcomeMapper.mapEntityToResponseDTO);
        }
    }

    private Outcome findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageEnum.OUTCOME_NOT_FOUND_WITH_ID.getMessage(String.valueOf(
                        id))));
    }

    private void updateParameterForInstallmentsOutcome(OutcomeRequestDTO request, Outcome entity, Date parentDate) {
        var OUTCOME_WITH_INSTALLMENTS_REGEX = ".*( - (\\d+)/(\\d+))$";
        var CURRENT_INSTALLMENT_PART = 2;

        var regexPattern = Pattern.compile(OUTCOME_WITH_INSTALLMENTS_REGEX);
        var matcher = regexPattern.matcher(entity.getDescription());
        if (matcher.find()) {
            var currentInstallment = Integer.parseInt(matcher.group(CURRENT_INSTALLMENT_PART));

            entity.setDescription(request.getDescription() + " - " + currentInstallment + "/" + request.getInstallments());
            entity.setDate(DateUtils.addMonthsToDate(parentDate, currentInstallment - 1));
        } else {
            entity.setDescription(request.getDescription());
            entity.setDate(new Date(request.getDate()));
        }
        entity.setValue(request.getValue());
        if (request.getCategoryId() != null) {
            var category = categoryService.findByIdOrThrow(request.getCategoryId());
            entity.setCategory(category);
        }
        if (request.getPaymentMethodId() != null) {
            var paymentMethod = paymentMethodService.findByIdOrThrow(request.getPaymentMethodId());
            entity.setPaymentMethod(paymentMethod);
        }
        if (request.getBankId() != null) {
            var bank = bankService.findByIdOrThrow(request.getBankId());
            entity.setBank(bank);
        }
    }

    @Transactional
    public OutcomeResponseDTO update(Long id, OutcomeRequestDTO request) {
        var PARENT_INSTALLMENT = 1;

        log.info(MessageEnum.OUTCOME_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        var oldOutcome = findByIdOrThrow(id);
        var childrenOutcomes = oldOutcome.getChildrenInstallments();
        int installmentsSize = childrenOutcomes.size() + PARENT_INSTALLMENT;
        var installmentsDiff = request.getInstallments() - installmentsSize;
        if (installmentsDiff > 0) {
            for (int i = 1; i <= installmentsDiff; i++) {
                var installmentNumber = installmentsSize + i;
                var newOutcome = buildInstallmentOutcome(
                        request,
                        oldOutcome,
                        installmentNumber
                );
                childrenOutcomes.add(newOutcome);
            }
            log.info(MessageEnum.OUTCOME_CREATED_MULTIPLE.getMessage(String.valueOf(installmentsDiff)));
        } else if (installmentsDiff < 0) {
            for (int i = 0; i < Math.abs(installmentsDiff); i++) {
                var removedOutcome = childrenOutcomes.removeLast();
                repository.delete(removedOutcome);
                log.info(MessageEnum.OUTCOME_DELETED_WITH_ID.getMessage(String.valueOf(removedOutcome.getId())));
            }
        }
        childrenOutcomes = oldOutcome.getChildrenInstallments();
        if (childrenOutcomes.isEmpty()) {
            setOutcomeParametersFromRequest(request, oldOutcome);
            var updatedOutcome = save(oldOutcome);
            log.info(MessageEnum.OUTCOME_UPDATED_WITH_ID.getMessage(String.valueOf(updatedOutcome.getId())));
            return convertToSingleDTO(updatedOutcome, OutcomeResponseDTO.class, OutcomeMapper.mapEntityToResponseDTO);
        } else {
            updateParameterForInstallmentsOutcome(request, oldOutcome, new Date(request.getDate()));
            var updatedOutcome = repository.save(oldOutcome);
            log.info(MessageEnum.OUTCOME_UPDATED_WITH_ID.getMessage(String.valueOf(oldOutcome.getId())));
            for (var child : childrenOutcomes) {
                updateParameterForInstallmentsOutcome(request, child, updatedOutcome.getDate());
                repository.save(child);
                log.info(MessageEnum.OUTCOME_UPDATED_WITH_ID.getMessage(String.valueOf(child.getId())));
            }
            return convertToSingleDTO(updatedOutcome, OutcomeResponseDTO.class, OutcomeMapper.mapEntityToResponseDTO);
        }
    }

    @Transactional
    public OutcomeResponseDTO getById(Long id) {
        log.info(MessageEnum.OUTCOME_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var outcome = findByIdOrThrow(id);
        return convertToSingleDTO(outcome, OutcomeResponseDTO.class, OutcomeMapper.mapEntityToResponseDTO);
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
        if (outcomeFilter.getHideInstallments() != null && outcomeFilter.getHideInstallments())
            predicates.add(criteriaBuilder.isNull(from.get("installmentParent")));
    }

    private Specification<Outcome> generateSpecification(OutcomeFilterRequestDTO outcomeFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            addPredicates(outcomeFilter, predicates, criteriaBuilder, root);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Transactional
    public Page<OutcomeResponseDTO> getAllPageable(OutcomeFilterRequestDTO outcomeFilter) {
        log.info(MessageEnum.OUTCOME_FINDING_ALL_PAGEABLE.getMessage());
        var sort = getSorting(outcomeFilter.getOrderBy()).and(Sort.by("id").descending());
        var spec = generateSpecification(outcomeFilter);
        Page<Outcome> pageOutcomes;
        if (outcomeFilter.getFindAll()) {
            pageOutcomes = repository.findAll(spec, Pageable.unpaged(sort));
        } else {
            var pageable = generatePageable(outcomeFilter.getPage(), outcomeFilter.getSize(), sort);
            pageOutcomes = repository.findAll(spec, pageable);
        }
        log.info(MessageEnum.OUTCOME_FOUND_ALL_PAGEABLE.getMessage(String.valueOf(pageOutcomes.getNumberOfElements())));
        return convertToPageDTO(pageOutcomes, OutcomeResponseDTO.class, OutcomeMapper.mapEntityToResponseDTO);
    }

    private static BigDecimal calculateTotalValueOfOutcomes(List<Outcome> outcomes) {
        return outcomes.stream().map(Outcome::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @param outcomes list of outcomes
     * @return a list of the categories and the value expended on them ordered by categories that were most expended
     */
    private List<OutcomeKpiByCategoryResponseDTO> groupByCategory(
            List<Outcome> outcomes
    ) {
        var kpiByCategoryList = new ArrayList<OutcomeKpiByCategoryResponseDTO>();
        outcomes.stream()
                .collect(
                        Collectors.groupingBy(
                                Outcome::getCategory,
                                Collectors.reducing(BigDecimal.ZERO, Outcome::getValue, BigDecimal::add)
                        )
                )
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> kpiByCategoryList.add(new OutcomeKpiByCategoryResponseDTO(
                        entry.getValue(),
                        convertToSingleDTO(entry.getKey(), OutcomeCategoryResponseDTO.class)
                )));
        return kpiByCategoryList;
    }

    public OutcomeKpiResponseDTO getKpi(OutcomeFilterRequestDTO filter) {
        log.info(MessageEnum.OUTCOME_FINDING_KPI.getMessage());
        var spec = generateSpecification(filter);
        var outcomes = repository.findAll(spec);
        log.info(MessageEnum.OUTCOME_FOUND_KPI.getMessage(String.valueOf(outcomes.size())));

        var totalValue = calculateTotalValueOfOutcomes(outcomes);
        var kpiByCategoryList = groupByCategory(outcomes);

        return new OutcomeKpiResponseDTO(totalValue, kpiByCategoryList);
    }

    public void delete(Long id) {
        log.info(MessageEnum.OUTCOME_DELETING_WITH_ID.getMessage(String.valueOf(id)));
        var outcome = findByIdOrThrow(id);
        var childrenOutcomes = outcome.getChildrenInstallments();
        for (var child : childrenOutcomes) {
            repository.delete(child);
            log.info(MessageEnum.OUTCOME_DELETED_WITH_ID.getMessage(String.valueOf(child.getId())));
        }
        repository.delete(outcome);
        log.info(MessageEnum.OUTCOME_DELETED_WITH_ID.getMessage(String.valueOf(id)));
    }
}
