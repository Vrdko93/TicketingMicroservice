package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.service.PDFService;

@RestController
public class PDFController {

	@Value("${pdf.save.path}")
	private String pdfSavePath;

	@Autowired
	private PDFService pdfService;
	    
	@GetMapping("/generateAndSave/{content}")
	public ResponseEntity<String> generateAndSavePdf(@PathVariable String content) {

		try {

			// Call service method
	        pdfService.generateAndSavePdf(content, pdfSavePath + "/demo.pdf");

	        // Set response headers
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.TEXT_PLAIN);

	        // Return success response
	        return ResponseEntity.ok().headers(headers).body("OK");

	    } catch (Exception e) {

	    	// Print exception
	        System.out.println(e.getMessage());

	        // Return error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
	        }
	    }

	@GetMapping("/generateAndSendPdf/{content}/{email}")
	public ResponseEntity<String> generateAndSendPdf(@PathVariable String content, @PathVariable String email) {

		try {

			// Call service method
	        String response = pdfService.generatePdfAndSendEmail(content, email, pdfSavePath);

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {

	    	System.out.println(e.getMessage());

	        return ResponseEntity.status(500).body("Error generating PDF or sending email.");
	    }
	}
}
