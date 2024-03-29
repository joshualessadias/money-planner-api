package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.OutcomeFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.OutcomeRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.services.OutcomeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/outcome")
public class OutcomeController {
    private final OutcomeService outcomeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOutcome(
            @Valid @RequestBody OutcomeRequestDTO outcomeRequestDto
    ) {
        outcomeService.create(outcomeRequestDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OutcomeResponseDTO getOutcomeById(
            @PathVariable Long id
    ) {
        return outcomeService.getById(id);
    }

    @GetMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<OutcomeResponseDTO> getOutcomePageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "categoryId", required = false) Long categoryId,
            @RequestParam(name = "paymentMethodId", required = false) Long paymentMethodId,
            @RequestParam(name = "bankId", required = false) Long bankId,
            @RequestParam(name = "initialDate", required = false) Long initialDate,
            @RequestParam(name = "finalDate", required = false) Long finalDate,
            @RequestParam(name = "initialValue", required = false) BigDecimal initialValue,
            @RequestParam(name = "finalValue", required = false) BigDecimal finalValue,
            @RequestParam(name = "description", required = false) String description
    ) {
        var outcomeFilter = OutcomeFilterRequestDTO.builder()
                .page(page)
                .size(size)
                .orderBy(orderBy)
                .categoryId(categoryId)
                .paymentMethodId(paymentMethodId)
                .bankId(bankId)
                .initialDate(initialDate == null ? null : new Date(initialDate))
                .finalDate(finalDate == null ? null : new Date(finalDate))
                .initialValue(initialValue)
                .finalValue(finalValue)
                .description(description)
                .build();

        return outcomeService.getAllPageable(outcomeFilter);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOutcome(
            @PathVariable Long id, @Valid @RequestBody OutcomeRequestDTO outcomeRequestDto
    ) {
        outcomeService.update(id, outcomeRequestDto);
    }
}
