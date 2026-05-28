package com.synergisticit.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.synergisticit.enums.ActionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class TicketHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
    @Transient
    private Long ticketId;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
	private ActionType action;      // CREATED, APPROVED, REJECTED, ASSIGNED, RESOLVED, CLOSED, REOPENED
    
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "action_by", nullable = true)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Employee actionBy;
	
	@JsonFormat(pattern = "MM-dd-yyyy hh:mm a")
	private LocalDateTime actionDate;
	private String comments;

	public TicketHistory() {}

	public TicketHistory(Long id, Ticket ticket, ActionType action, Employee actionBy, LocalDateTime actionDate, String comments) {
		super();
		this.id = id;
		this.ticket = ticket;
		this.action = action;
		this.actionBy = actionBy;
		this.actionDate = actionDate;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public ActionType getAction() {
		return action;
	}

	public void setAction(ActionType action) {
		this.action = action;
	}

	public Employee getActionBy() {
		return actionBy;
	}

	public void setActionBy(Employee actionBy) {
		this.actionBy = actionBy;
	}

	public LocalDateTime getActionDate() {
		return actionDate;
	}

	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
}
