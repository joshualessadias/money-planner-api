package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.base.AbstractServiceRepository;
import com.joshuadias.moneyplannerapi.dto.requests.spendingGoal.SpendingGoalRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.spendingGoal.SpendingGoalResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.models.SpendingGoal;
import com.joshuadias.moneyplannerapi.repositories.SpendingGoalRepository;
import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class SpendingGoalService extends AbstractServiceRepository<SpendingGoalRepository, SpendingGoal, Long> {

    private SpendingGoal buildEntityFromRequest(SpendingGoalRequestDTO request) {
        return convertToSingleDTO(request, SpendingGoal.class);
    }

    @Transactional
    public SpendingGoalResponseDTO create(SpendingGoalRequestDTO request) {
        log.info(MessageEnum.SPENDING_GOAL_CREATING.getMessage());
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
}
