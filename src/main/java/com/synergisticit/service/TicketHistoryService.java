package com.synergisticit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.enums.ActionType;
import com.synergisticit.model.Employee;
import com.synergisticit.model.Ticket;
import com.synergisticit.model.TicketHistory;
import com.synergisticit.repository.TicketHistoryRepository;

@Service
public class TicketHistoryService {

	@Autowired
	private TicketHistoryRepository historyRepository;

	public TicketHistoryService() {}
	
	public TicketHistory addHistory(Ticket ticket, ActionType action, Employee actionBy, String comments) {

		TicketHistory history = new TicketHistory();
		history.setTicket(ticket);
		history.setAction(action);
		history.setActionBy(actionBy);
		history.setActionDate(LocalDateTime.now());
		history.setComments(comments);

		return historyRepository.save(history);
	}

	public List<TicketHistory> getHistoryByTicket(Ticket ticket) {
		return historyRepository.findByTicket(ticket);
	}

	public List<TicketHistory> getAllHistory() {
		return historyRepository.findAll();
	}
}
