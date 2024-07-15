package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.spendingGoal.SpendingGoalRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.spendingGoal.SpendingGoalResponseDTO;
import com.joshuadias.moneyplannerapi.models.SpendingGoal;
import com.joshuadias.moneyplannerapi.services.SpendingGoalService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/spending-goal")
public class SpendingGoalController {

    private final SpendingGoalService service;

    @PostMapping
    public ResponseEntity<SpendingGoalResponseDTO> createSpendingGoal(
            @Valid @RequestBody SpendingGoalRequestDTO spendingGoalRequestDTO
    ) {
        return new ResponseEntity<>(service.create(spendingGoalRequestDTO), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpendingGoalResponseDTO> getSpendingGoalById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<SpendingGoalResponseDTO>> getSpendingGoalList(
            Pageable pageable,
            @QuerydslPredicate(root = SpendingGoal.class) Predicate predicate
    ) {
        return new ResponseEntity<>(service.getPageable(pageable, predicate), OK);
    }
}
