package com.synergisticit.service;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.synergisticit.repository.PDFRepository;

@Service
public class PDFService {

	 	@Autowired
	    private PDFRepository pdfRepository;

	    // Generate PDF and send email
//	    public String generatePdfAndSendEmail(String content, String email, String path) throws Exception {
//
//	    	pdfRepository.sendEmailWithAttachment(email, path);
//
//	        return "success";
//	    }

	    // Generate PDF as byte array
//	    public byte[] generatePdf(String content) throws IOException {
//
//	        return pdfRepository.generatePdf(content);
//	    }

	    // Generate and save PDF
	    public String generateAndSavePdf(String content, String path) throws IOException {
		    Document document = new Document();

		    FileOutputStream fileOutputStream = new FileOutputStream(path);

		    try {

		        PdfWriter.getInstance(document, fileOutputStream);

		        document.open();

		        document.add(new Paragraph("Dynamic PDF Content"));
		        document.add(new Paragraph(content));

		        document.close();

		    } catch (Exception e) {

		        System.out.println("Error: " + e);

		    } finally {

		        fileOutputStream.close();
		    }

		    return path;
	    }
}
