package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synergisticit.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	List<Ticket> findByCreatedBy_Id(Long id);
	List<Ticket> findByAssignee_Id(Long id);

}
