package com.joshuadias.moneyplannerapi.domains.core.controllers;

import com.joshuadias.moneyplannerapi.domains.docparser.services.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class PdfController {
    private final PdfService service;

    @PostMapping("/import")
    public ResponseEntity<String> importPdf(
            @RequestParam
            MultipartFile file
    ) {
        return new ResponseEntity<>(service.extractTextFromPdf(file), ACCEPTED);
    }
}
