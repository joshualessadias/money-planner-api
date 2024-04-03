package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.OutcomeCategoryRequestDTO;
import com.joshuadias.moneyplannerapi.services.OutcomeCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
