package hello.hellospring.controller;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.TicketDTO;
import hello.hellospring.dto.TicketResponseDTO;
import hello.hellospring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        TicketResponseDTO responseDTO = ticketService.convertToDTO(createdTicket);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Ticket>> getAllTicketsOfProjectId(@PathVariable int projectId) {
        List<Ticket> tickets = ticketService.getAllTicketsOfProjectId(projectId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/project/{projectId}/ticket/{id}")
    public ResponseEntity<Ticket> getTicketByIdAndProjectId(@PathVariable int projectId, @PathVariable int id) {
        Ticket ticket = ticketService.getTicketByIdAndProjectId(id, projectId);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable int id, @RequestBody TicketDTO ticketDTO) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDTO);
        TicketResponseDTO responseDTO = ticketService.convertToDTO(updatedTicket);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/developer/{developerId}")
    public List<TicketResponseDTO> getTicketsAssignedToDeveloper(@PathVariable int developerId) {
        return ticketService.getTicketsAssignedToDeveloper(developerId);
    }

    @GetMapping("/tester/{testerId}/fixed")
    public List<TicketResponseDTO> getFixedTicketsReportedByTester(@PathVariable int testerId) {
        return ticketService.getFixedTicketsReportedByTester(testerId);
    }

    @GetMapping("/state/{state}")
    public List<TicketResponseDTO> getTicketsByState(@PathVariable State state) {
        return ticketService.getTicketsByState(state);
    }
}