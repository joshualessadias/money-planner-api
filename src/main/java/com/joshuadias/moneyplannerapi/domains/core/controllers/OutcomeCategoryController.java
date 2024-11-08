package com.joshuadias.moneyplannerapi.domains.core.controllers;

import com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcomeCategory.OutcomeCategoryFilterRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.outcomeCategory.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.domains.core.services.OutcomeCategoryService;
import com.joshuadias.moneyplannerapi.domains.core.utils.OrderByUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/outcome-category")
public class OutcomeCategoryController {
    private final OutcomeCategoryService outcomeCategoryService;

    @PostMapping
    public ResponseEntity<OutcomeCategoryResponseDTO> createOutcome(
            @Valid @RequestBody OutcomeCategoryRequestDTO outcomeCategoryRequestDTO
    ) {
        return new ResponseEntity<>(outcomeCategoryService.create(outcomeCategoryRequestDTO), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutcomeCategoryResponseDTO> getOutcomeCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(outcomeCategoryService.getById(id), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutcomeCategoryResponseDTO>> getOutcomeCategoryList(
            @RequestParam(name = "orderBy", required = false) String orderBy
    ) {
        return new ResponseEntity<>(outcomeCategoryService.getAll(orderBy), OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<OutcomeCategoryResponseDTO>> getOutcomePageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = new OutcomeCategoryFilterRequestDTO();
        filter.setPage(page);
        filter.setSize(size);
        filter.setOrderBy(orderBy);
        filter.setName(name);
        filter.setDescription(description);

        return new ResponseEntity<>(outcomeCategoryService.getAllPageable(filter), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutcomeCategoryResponseDTO> updateOutcomeCategory(
            @PathVariable Long id,
            @Valid @RequestBody OutcomeCategoryRequestDTO outcomeCategoryRequestDTO
    ) {
        return new ResponseEntity<>(outcomeCategoryService.update(id, outcomeCategoryRequestDTO), ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutcomeCategory(@PathVariable Long id) {
        outcomeCategoryService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
