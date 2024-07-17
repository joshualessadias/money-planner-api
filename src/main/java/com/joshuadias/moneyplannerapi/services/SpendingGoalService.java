package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.dto.requests.spendingGoal.CategorySpendingGoalRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.spendingGoal.SpendingGoalRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.spendingGoal.SpendingGoalResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.helpers.SpendingGoalHelper;
import com.joshuadias.moneyplannerapi.models.SpendingGoal;
import com.joshuadias.moneyplannerapi.repositories.SpendingGoalRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpendingGoalService extends AbstractServiceRepository<SpendingGoalRepository, SpendingGoal, Long> {

    private SpendingGoal buildEntityFromRequest(SpendingGoalRequestDTO request) {
        var entity = convertToSingleDTO(request, SpendingGoal.class);
        SpendingGoalHelper.setCustomParameters(entity);
        return entity;
    }

    private void checkRepeatedOutcomeCategory(SpendingGoalRequestDTO request) {
        var outcomeCategoryIdList = request.getCategorySpendingGoalList()
                .stream()
                .map(CategorySpendingGoalRequestDTO::getOutcomeCategoryId)
                .toList();
        if (outcomeCategoryIdList.size() != outcomeCategoryIdList.stream().distinct().count()) {
            throw new BadRequestException(MessageEnum.SPENDING_GOAL_REPEATED_OUTCOME_CATEGORY.getMessage());
        }
    }

    private void validateRequest(SpendingGoalRequestDTO request) {
        var valueLimit = request.getValue();
        var categorySpendingGoalListValueSum = BigDecimal.ZERO;

        if (request.getCategorySpendingGoalList() == null || request.getCategorySpendingGoalList().isEmpty()) {
            return;
        }
        checkRepeatedOutcomeCategory(request);
        for (var categorySpendingGoal : request.getCategorySpendingGoalList()) {
            if (categorySpendingGoal.getIsPercentual() != null && categorySpendingGoal.getIsPercentual()) {
                var absoluteValue = valueLimit.multiply(categorySpendingGoal.getValue()
                                                                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
                categorySpendingGoalListValueSum = categorySpendingGoalListValueSum.add(absoluteValue);
            } else {
                categorySpendingGoalListValueSum = categorySpendingGoalListValueSum.add(categorySpendingGoal.getValue());
            }
        }

        if (categorySpendingGoalListValueSum.compareTo(valueLimit) > 0) {
            throw new BadRequestException(MessageEnum.SPENDING_GOAL_VALUE_LIMIT_EXCEEDED.getMessage(
                    categorySpendingGoalListValueSum.toString(),
                    valueLimit.toString()
            ));
        }
    }

    @Transactional
    public SpendingGoalResponseDTO create(SpendingGoalRequestDTO request) {
        log.info(MessageEnum.SPENDING_GOAL_CREATING.getMessage());
        validateRequest(request);
        var entity = buildEntityFromRequest(request);
        var createdEntity = save(entity);
        log.info(MessageEnum.SPENDING_GOAL_CREATED_WITH_ID.getMessage(String.valueOf(createdEntity.getId())));
        return convertToSingleDTO(createdEntity, SpendingGoalResponseDTO.class);
    }

    private SpendingGoal findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException(MessageEnum.SPENDING_GOAL_NOT_FOUND_WITH_ID.getMessage(String.valueOf(
                        id))));
    }

    public SpendingGoalResponseDTO getById(Long id) {
        log.info(MessageEnum.SPENDING_GOAL_FINDING_BY_ID.getMessage(String.valueOf(id)));
        var entity = findByIdOrThrow(id);
        return convertToSingleDTO(entity, SpendingGoalResponseDTO.class);
    }

    public Page<SpendingGoalResponseDTO> getPageable(Pageable pageable, Predicate predicate) {
        var entityPageable = repository.findAll(predicate, pageable);
        return convertToPageDTO(entityPageable, SpendingGoalResponseDTO.class);
    }

    public SpendingGoalResponseDTO update(Long id, SpendingGoalRequestDTO request) {
        log.info(MessageEnum.SPENDING_GOAL_UPDATING_WITH_ID.getMessage(String.valueOf(id)));
        validateRequest(request);
        var entityFound = findByIdOrThrow(id);
        var entity = buildEntityFromRequest(request);
        entity.setId(entityFound.getId());
        var updatedEntity = save(entity);
        log.info(MessageEnum.SPENDING_GOAL_UPDATED_WITH_ID.getMessage(String.valueOf(updatedEntity.getId())));
        return convertToSingleDTO(updatedEntity, SpendingGoalResponseDTO.class);
    }

    public void delete(Long id) {
        // TODO: Implement this method
        throw new UnsupportedOperationException();
    }
}
