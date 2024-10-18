package com.joshuadias.moneyplannerapi.domains.docparser.services;

import org.springframework.web.multipart.MultipartFile;

public interface PdfService {

    String extractTextFromPdf(MultipartFile file);
}
