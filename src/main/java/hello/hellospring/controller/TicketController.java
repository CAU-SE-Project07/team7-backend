package hello.hellospring.controller;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Priority;
import hello.hellospring.domain.State;
import hello.hellospring.domain.Ticket;
import hello.hellospring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.addTicket(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Ticket>> getTicketsByTitle(@PathVariable String title) {
        List<Ticket> tickets = ticketService.findByTitle(title);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/reporter/{reporter}")
    public ResponseEntity<List<Ticket>> getTicketsByReporter(@PathVariable String reporter) {
        List<Ticket> tickets = ticketService.findByReporter(reporter);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Ticket>> getTicketsByDate(@PathVariable Date date) {
        List<Ticket> tickets = ticketService.findByDate(date);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/fixer/{fixer}")
    public ResponseEntity<List<Ticket>> getTicketsByFixer(@PathVariable String fixer) {
        List<Ticket> tickets = ticketService.findByFixer(fixer);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/assignee/{assignee}")
    public ResponseEntity<List<Ticket>> getTicketsByAssignee(@PathVariable String assignee) {
        List<Ticket> tickets = ticketService.findByAssignee(assignee);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Ticket>> getTicketsByPriority(@PathVariable Priority priority) {
        List<Ticket> tickets = ticketService.findByPriority(priority);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Ticket>> getTicketsByState(@PathVariable State state) {
        List<Ticket> tickets = ticketService.findByState(state);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/allTickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping("/{ticketId}/comments")
    public ResponseEntity<Ticket> addCommentToTicket(@PathVariable int ticketId, @RequestBody Comment comment) {
        Ticket updatedTicket = ticketService.addCommentToTicket(ticketId, comment);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int ticketId, @RequestBody Ticket ticket) {
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticket);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

}

