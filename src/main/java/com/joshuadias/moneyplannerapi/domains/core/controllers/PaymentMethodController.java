package com.joshuadias.moneyplannerapi.domains.core.controllers;

import com.joshuadias.moneyplannerapi.domains.core.dto.requests.paymentMethod.PaymentMethodFilterRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.requests.paymentMethod.PaymentMethodRequestDTO;
import com.joshuadias.moneyplannerapi.domains.core.dto.responses.PaymentMethodResponseDTO;
import com.joshuadias.moneyplannerapi.domains.core.services.PaymentMethodService;
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
@RequestMapping("/api/v1/payment-method")
public class PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping
    public ResponseEntity<PaymentMethodResponseDTO> create(@Valid @RequestBody PaymentMethodRequestDTO request) {
        return new ResponseEntity<>(service.create(request), CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id), OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentMethodResponseDTO>> getList(
            @RequestParam(name = "orderBy", required = false) String orderBy
    ) {
        return new ResponseEntity<>(service.getAll(orderBy), OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<PaymentMethodResponseDTO>> getOutcomePageable(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "orderBy", required = false) String orderBy,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "description", required = false) String description
    ) {
        OrderByUtils.validateOrderBy(orderBy);
        var filter = new PaymentMethodFilterRequestDTO();
        filter.setPage(page);
        filter.setSize(size);
        filter.setOrderBy(orderBy);
        filter.setName(name);
        filter.setCode(code);
        filter.setDescription(description);

        return new ResponseEntity<>(service.getAllPageable(filter), OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PaymentMethodRequestDTO request
    ) {
        return new ResponseEntity<>(service.update(id, request), ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
