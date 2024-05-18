package hello.hellospring.service;

import hello.hellospring.domain.Priority;
import hello.hellospring.domain.State;
import hello.hellospring.domain.Ticket;

import java.util.Date;
import java.util.List;

public interface TicketService {
    Ticket addTicket(Ticket ticket);
    List<Ticket> findByTitle(String title);
    List<Ticket> findByDescription(String description);
    List<Ticket> findByReporter(String reporter);
    List<Ticket> findByDate(Date date);
    List<Ticket> findByFixer(String fixer);
    List<Ticket> findByAssignee(String assignee);
    List<Ticket> findByPriority(Priority priority);
    List<Ticket> findByState(State state);
    List<Ticket> findAllTickets();
}