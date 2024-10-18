package com.joshuadias.moneyplannerapi.domains.docparser.services;

import com.joshuadias.moneyplannerapi.domains.shared.exceptions.InternalServerException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    @Override
    public String extractTextFromPdf(MultipartFile file) {
        try {
            var document = Loader.loadPDF(file.getBytes());
            var pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }
    }
}
