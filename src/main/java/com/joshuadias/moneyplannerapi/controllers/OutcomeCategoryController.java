package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.OutcomeCategoryFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.OutcomeCategoryResponseDTO;
import com.joshuadias.moneyplannerapi.services.OutcomeCategoryService;
import com.joshuadias.moneyplannerapi.utils.OrderByUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/outcome-category")
public class OutcomeCategoryController {
    private final OutcomeCategoryService outcomeCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOutcome(@Valid @RequestBody OutcomeCategoryRequestDTO outcomeCategoryRequestDTO) {
        outcomeCategoryService.create(outcomeCategoryRequestDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OutcomeCategoryResponseDTO getOutcomeCategoryById(@PathVariable Long id) {
        return outcomeCategoryService.getById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<OutcomeCategoryResponseDTO> getOutcomeCategoryList() {
        return outcomeCategoryService.getAll();
    }

    @GetMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    public Page<OutcomeCategoryResponseDTO> getOutcomePageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description", required = false) String description
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var outcomeCategoryFilter = OutcomeCategoryFilterRequestDTO.builder()
                .page(page)
                .size(size)
                .orderBy(orderBy)
                .name(name)
                .description(description)
                .build();

        return outcomeCategoryService.getAllPageable(outcomeCategoryFilter);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOutcomeCategory(
            @PathVariable Long id,
            @Valid @RequestBody OutcomeCategoryRequestDTO outcomeCategoryRequestDTO
    ) {
        outcomeCategoryService.update(id, outcomeCategoryRequestDTO);
    }
}
