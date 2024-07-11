package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.outcome.OutcomeRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeKpiResponseDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeResponseDTO;
import com.joshuadias.moneyplannerapi.services.OutcomeService;
import com.joshuadias.moneyplannerapi.utils.OrderByUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/outcome")
public class OutcomeController {
    private final OutcomeService outcomeService;

    @PostMapping
    public ResponseEntity<OutcomeResponseDTO> createOutcome(@Valid @RequestBody OutcomeRequestDTO outcomeRequestDto) {
        return new ResponseEntity<>(outcomeService.create(outcomeRequestDto), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutcomeResponseDTO> getOutcomeById(@PathVariable Long id) {
        return new ResponseEntity<>(outcomeService.getById(id), OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<OutcomeResponseDTO>> getOutcomePageable(
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
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "findAll", defaultValue = "0") Boolean findAll,
            @RequestParam(name = "hideInstallments", defaultValue = "0") Boolean hideInstallments
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = new OutcomeFilterRequestDTO();
        filter.setPage(page);
        filter.setSize(size);
        filter.setOrderBy(orderBy);
        filter.setCategoryId(categoryId);
        filter.setPaymentMethodId(paymentMethodId);
        filter.setBankId(bankId);
        filter.setInitialDate(initialDate == null ? null : new Date(initialDate));
        filter.setFinalDate(finalDate == null ? null : new Date(finalDate));
        filter.setInitialValue(initialValue);
        filter.setFinalValue(finalValue);
        filter.setDescription(description);
        filter.setFindAll(findAll);
        filter.setHideInstallments(hideInstallments);

        return new ResponseEntity<>(outcomeService.getAllPageable(filter), OK);
    }

    @GetMapping("/kpi")
    public ResponseEntity<OutcomeKpiResponseDTO> getOutcomeKpi(
            @RequestParam(name = "initialDate", required = false) Long initialDate,
            @RequestParam(name = "finalDate", required = false) Long finalDate
    ) {
        var filter = new OutcomeFilterRequestDTO();
        filter.setInitialDate(initialDate == null ? null : new Date(initialDate));
        filter.setFinalDate(finalDate == null ? null : new Date(finalDate));

        return new ResponseEntity<>(outcomeService.getKpi(filter), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutcomeResponseDTO> updateOutcome(
            @PathVariable Long id,
            @Valid @RequestBody OutcomeRequestDTO outcomeRequestDto
    ) {
        return new ResponseEntity<>(outcomeService.update(id, outcomeRequestDto), ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutcome(@PathVariable Long id) {
        outcomeService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
