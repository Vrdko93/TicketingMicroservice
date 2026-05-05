package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.synergisticit.model.Ticket;
import com.synergisticit.model.TicketHistory;
import com.synergisticit.service.TicketHistoryService;
import com.synergisticit.service.TicketService;

@RestController
public class TicketHistoryController {
	
	@Autowired
    private TicketHistoryService historyService;
	
	@Autowired
    private TicketService ticketService;

	public TicketHistoryController() {}
	
	@GetMapping("/history")
	public ResponseEntity<List<TicketHistory>> getAllHistory() {
		return ResponseEntity.ok(historyService.getAllHistory());
	}

	@GetMapping("/history/ticket/{ticketId}")
	public ResponseEntity<List<TicketHistory>> getHistoryByTicket(@PathVariable Long ticketId) {

		Ticket ticket = ticketService.getTicketById(ticketId);

	    return ResponseEntity.ok(historyService.getHistoryByTicket(ticket));
	}
}
