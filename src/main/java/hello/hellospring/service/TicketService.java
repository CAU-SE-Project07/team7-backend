package hello.hellospring.service;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.TicketDTO;
import hello.hellospring.dto.TicketResponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TicketService {
    public List<TicketResponseDTO> getTicketsAssignedToDeveloper(int developerId);
    public List<TicketResponseDTO> getFixedTicketsReportedByTester(int testerId);
    public List<TicketResponseDTO> getTicketsByState(State state);
    public Ticket createTicket(TicketDTO ticketDTO);
    List<Ticket> getAllTicketsOfProjectId(int projectId);
    Ticket getTicketByIdAndProjectId(int id, int projectId);
    public Ticket updateTicket(int id, TicketDTO ticketDTO);
    public void deleteTicket(int id);
    public TicketResponseDTO convertToDTO(Ticket ticket);


}