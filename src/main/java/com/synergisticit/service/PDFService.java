package com.synergisticit.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.repository.PDFRepository;

@Service
public class PDFService {

	 	@Autowired
	    private PDFRepository pdfRepository;

	    public String generatePdfAndSendEmail(String content, String email, String pdfSavePath) throws Exception {

	    	return pdfRepository.generatePdfAndSendEmail(content, email, pdfSavePath);
	    }
	   
//	    public byte[] generatePdf(String content) throws IOException {
//
//	        return pdfRepository.generatePdf(content);
//	    }
	    
	    public String generateAndSavePdf(String content, String path) throws IOException {

		    return pdfRepository.generateAndSavePdf(content, path);
	    }
}
