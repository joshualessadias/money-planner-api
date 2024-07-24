package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.bank.BankFilterRequestDTO;
import com.joshuadias.moneyplannerapi.dto.requests.bank.BankRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.BankResponseDTO;
import com.joshuadias.moneyplannerapi.services.BankService;
import com.joshuadias.moneyplannerapi.utils.OrderByUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    private final BankService service;

    @PostMapping
    public ResponseEntity<BankResponseDTO> create(@Valid @RequestBody BankRequestDTO request) {
        return new ResponseEntity<>(service.create(request), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponseDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankResponseDTO>> getList(
            @RequestParam(name = "orderBy", required = false) String orderBy
    ) {
        return new ResponseEntity<>(service.getAll(orderBy), OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<BankResponseDTO>> getOutcomePageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "code", required = false) String code
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = new BankFilterRequestDTO();
        filter.setPage(page);
        filter.setSize(size);
        filter.setOrderBy(orderBy);
        filter.setName(name);
        filter.setCode(code);

        return new ResponseEntity<>(service.getAllPageable(filter), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BankRequestDTO request
    ) {
        return new ResponseEntity<>(service.update(id, request), ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
