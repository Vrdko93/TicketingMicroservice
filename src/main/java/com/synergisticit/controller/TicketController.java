package com.synergisticit.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.enums.TicketStatus;
import com.synergisticit.model.Employee;
import com.synergisticit.model.Ticket;
import com.synergisticit.service.EmployeeService;
import com.synergisticit.service.TicketService;

@RestController
public class TicketController {

	@Autowired
    private TicketService ticketService;
	
	@Autowired
    private EmployeeService employeeService;
	
	public TicketController() {}
	
	@PostMapping("/tickets")
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
		
		return ResponseEntity.ok(ticketService.createTicket(ticket));
	}

	@GetMapping("/tickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		
	    return ResponseEntity.ok(ticketService.getAllTickets());
	}

	@GetMapping("/tickets/{id}")
	public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
		
	    return ResponseEntity.ok(ticketService.getTicketById(id));
	}

	@PutMapping("/tickets/{id}/assign")
	public ResponseEntity<Ticket> assignTicket(@PathVariable Long id, @RequestParam Long employeeId) {
		
	    return ResponseEntity.ok(ticketService.assignTicket(id, employeeId));
	}

	@PutMapping("/tickets/{id}/status")
	public ResponseEntity<Ticket> updateStatus(@PathVariable Long id,
	                                               @RequestParam TicketStatus status,
	                                               @RequestParam String comment,
	                                               @RequestParam Long employeeId) {

		Employee employee = employeeService.getEmployeeById(employeeId);

	    return ResponseEntity.ok( ticketService.updateStatus(id, status, comment, employee));
	}
	
	@PutMapping("/tickets/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {

		Ticket updatedTicket = ticketService.updateTicket(id, ticket);
		
		return ResponseEntity.ok(updatedTicket);
	}
	
	@DeleteMapping("/tickets/{id}")
	public ResponseEntity<String> deleteTicket(@PathVariable Long id) {

	    ticketService.deleteTicket(id);
	    
	    return ResponseEntity.ok("Ticket deleted");
	}
	
	@GetMapping("/tickets/download/{id}")
	public ResponseEntity<byte[]> downloadTicket(@PathVariable Long id) throws Exception {

	    Ticket ticket = ticketService.getTicketById(id);

	    ByteArrayOutputStream baos = ticketService.generateTicketPdf(ticket);

	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "attachment; filename=ticket_" + id + ".pdf")
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(baos.toByteArray());
	}
	
	@PostMapping("/tickets/{id}/email")
	public ResponseEntity<String> sendTicketEmail(@PathVariable Long id) throws Exception {

	    ticketService.sendTicketEmail(id);

	    return ResponseEntity.ok("Email sent successfully");
	}
}
