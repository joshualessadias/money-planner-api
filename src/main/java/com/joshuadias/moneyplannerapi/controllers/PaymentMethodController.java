package com.joshuadias.moneyplannerapi.controllers;

import com.joshuadias.moneyplannerapi.dto.requests.paymentMethod.PaymentMethodRequestDTO;
import com.joshuadias.moneyplannerapi.dto.responses.PaymentMethodResponseDTO;
import com.joshuadias.moneyplannerapi.services.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment-method")
public class PaymentMethodController {

    private final PaymentMethodService service;

    @PostMapping
    public ResponseEntity<PaymentMethodResponseDTO> create(@Valid @RequestBody PaymentMethodRequestDTO request) {
        return new ResponseEntity<>(service.create(request), CREATED);
    }
}
