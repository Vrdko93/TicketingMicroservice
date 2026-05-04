package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.synergisticit.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
