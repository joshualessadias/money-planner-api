package com.joshuadias.moneyplannerapi.services;

import com.joshuadias.moneyplannerapi.dto.requests.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.enums.MessageEnum;
import com.joshuadias.moneyplannerapi.exceptions.BadRequestException;
import com.joshuadias.moneyplannerapi.exceptions.NotFoundException;
import com.joshuadias.moneyplannerapi.models.OutcomeCategory;
import com.joshuadias.moneyplannerapi.repositories.OutcomeCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OutcomeCategoryService
        extends AbstractServiceRepository<OutcomeCategoryRepository, OutcomeCategory, Long> {

    private OutcomeCategory buildOutcomeCategoryFromRequest(OutcomeCategoryRequestDTO outcomeCategoryRequestDto) {
        var outcomeCategory = new OutcomeCategory();
        outcomeCategory.setName(outcomeCategoryRequestDto.getName());
        outcomeCategory.setDescription(outcomeCategoryRequestDto.getDescription());
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
}
