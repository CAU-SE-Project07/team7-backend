package hello.hellospring.service;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Priority;
import hello.hellospring.domain.State;
import hello.hellospring.domain.Ticket;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Ticket addTicket(Ticket ticket)
    {
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(int ticketId, Ticket ticket) {
        Ticket existingTicket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setReporter(ticket.getReporter());
        existingTicket.setDate(ticket.getDate());
        existingTicket.setFixer(ticket.getFixer());
        existingTicket.setAssignee(ticket.getAssignee());
        existingTicket.setPriority(ticket.getPriority());
        existingTicket.setState(ticket.getState());
        return ticketRepository.save(existingTicket);
    }

    @Override
    public List<Ticket> findByTitle(String title) {
        return ticketRepository.findByTitle(title);
    }

    @Override
    public List<Ticket> findByDescription(String description) {
        return ticketRepository.findByDescription(description);
    }

    @Override
    public List<Ticket> findByReporter(String reporter) {
        return ticketRepository.findByReporter(reporter);
    }

    @Override
    public List<Ticket> findByDate(Date date) {
        return ticketRepository.findByDate(date);
    }

    @Override
    public List<Ticket> findByFixer(String fixer) {
        return ticketRepository.findByFixer(fixer);
    }

    @Override
    public List<Ticket> findByAssignee(String assignee) {
        return ticketRepository.findByAssignee(assignee);
    }

    @Override
    public List<Ticket> findByPriority(Priority priority) {
        return ticketRepository.findByPriority(priority);
    }

    @Override
    public List<Ticket> findByState(State state) {
        return ticketRepository.findByState(state);
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }
    @Override
    public Ticket addCommentToTicket(int ticketId, Comment comment) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        commentRepository.save(comment);
        ticket.getComments().add(comment);
        return ticketRepository.save(ticket);
    }


}
