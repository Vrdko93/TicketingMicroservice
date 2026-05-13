package com.synergisticit.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Repository
public class PDFRepository {
	
	@Autowired
    private JavaMailSender emailSender;

    public String generatePdfAndSendEmail(String content, String email, String pdfSavePath) throws IOException, MessagingException {

        // Define file path
        String filePath = pdfSavePath + "/demo.pdf";

        // Generate and save PDF
        generateAndSavePdf(content, filePath);

        // Send email with attachment
        sendEmailWithAttachment(email, "Dynamic PDF Report", "Please find the attached PDF.", filePath);

        return "PDF generated and email sent successfully!";
    }
    
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
    
    public void sendEmailWithAttachment(String to, String subject, String text, String filePath) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set email details
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // Attach PDF file
        File pdfFile = new File(filePath);

        helper.addAttachment(pdfFile.getName(), pdfFile);

        // Send email
        emailSender.send(message);
    }
}
