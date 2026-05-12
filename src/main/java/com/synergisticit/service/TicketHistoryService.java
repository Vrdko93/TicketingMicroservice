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

    public TicketHistory getHistoryById(Long id) {

        return historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found"));
    }
	
	public List<TicketHistory> getAllHistory() {
	    List<TicketHistory> list = historyRepository.findAll();

	    for (TicketHistory h : list) {

	        if (h.getTicket() != null) {
	            h.setTicketId(h.getTicket().getId());
	        }
	    }

	    return list;
	}
	
	public TicketHistory updateHistory(Long id, TicketHistory updatedHistory) {

	    TicketHistory existingHistory = historyRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("History not found"));

	    existingHistory.setAction(updatedHistory.getAction());
	    existingHistory.setComments(updatedHistory.getComments());
	    existingHistory.setActionDate(updatedHistory.getActionDate());

	    return historyRepository.save(existingHistory);
	}
	
	public void deleteHistory(Long id) {

		TicketHistory history = historyRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("History not found"));

	    historyRepository.delete(history);
	}
}
