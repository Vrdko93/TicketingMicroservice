package com.synergisticit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.enums.ActionType;
import com.synergisticit.enums.TicketStatus;
import com.synergisticit.model.Employee;
import com.synergisticit.model.Ticket;
import com.synergisticit.model.TicketHistory;
import com.synergisticit.repository.EmployeeRepository;
import com.synergisticit.repository.TicketHistoryRepository;
import com.synergisticit.repository.TicketRepository;

@Service
public class TicketService {
	
	@Autowired
    private TicketRepository ticketRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Autowired
    private TicketHistoryRepository historyRepository;

	public TicketService() {}
	
	public Ticket createTicket(Ticket ticket) {

		ticket.setStatus(TicketStatus.OPEN);
	    ticket.setCreationDate(LocalDateTime.now());

	    Ticket saved = ticketRepository.save(ticket);

	    addHistory(saved, ActionType.CREATED, ticket.getCreatedBy(), "Ticket created");

	    return saved;
	}

	public Ticket assignTicket(Long ticketId, Long employeeId) {

	    Ticket ticket = getTicketById(ticketId);

	    Employee assignee = employeeRepository.findById(employeeId)
	                .orElseThrow(() -> new RuntimeException("Employee not found"));

	    ticket.setAssignee(assignee);
	    ticket.setStatus(TicketStatus.ASSIGNED);

	    Ticket updated = ticketRepository.save(ticket);

	    addHistory(updated, ActionType.ASSIGNED, assignee, "Ticket assigned");

	    return updated;
	}

	public Ticket updateStatus(Long ticketId, TicketStatus status, String comment, Employee actionBy) {

	     Ticket ticket = getTicketById(ticketId);

	     ticket.setStatus(status);

	     Ticket updated = ticketRepository.save(ticket);

	     addHistory(updated,
	                mapStatusToAction(status),
	                actionBy,
	                comment);

	     return updated;
	 }

	 public List<Ticket> getAllTickets() {
	      return ticketRepository.findAll();
	 }

	 public Ticket getTicketById(Long id) {
	      return ticketRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Ticket not found"));
	 }

	  // 🔧 helper methods
	 private void addHistory(Ticket ticket, ActionType action, Employee employee, String comment) {

		 TicketHistory history = new TicketHistory();
	     history.setTicket(ticket);
	     history.setAction(action);
	     history.setActionBy(employee);
	     history.setActionDate(LocalDateTime.now());
	     history.setComments(comment);

	     historyRepository.save(history);
	 }

	  private ActionType mapStatusToAction(TicketStatus status) {
		  
	     return switch (status) {
	     	case APPROVED -> ActionType.APPROVED;
	        case REJECTED -> ActionType.REJECTED;
	        case ASSIGNED -> ActionType.ASSIGNED;
	        case RESOLVED -> ActionType.RESOLVED;
	        case CLOSED -> ActionType.CLOSED;
	        case REOPENED -> ActionType.REOPENED;
	        default -> ActionType.CREATED;
	      };
	  }
}
