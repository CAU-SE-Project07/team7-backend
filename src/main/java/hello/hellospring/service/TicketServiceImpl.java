package hello.hellospring.service;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Developer;
import hello.hellospring.domain.Tester;
import hello.hellospring.domain.enums.State;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.*;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.TesterRepository;
import hello.hellospring.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private DeveloperRepository developerRepository;


    public List<TicketResponseDTO> getTicketsAssignedToDeveloper(int developerId) {
        List<Ticket> tickets = ticketRepository.findByAssignedToDeveloperId(developerId);
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> getFixedTicketsReportedByTester(int testerId) {
        List<Ticket> tickets = ticketRepository.findFixedTicketsByReporterId(testerId);
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<TicketResponseDTO> getTicketsByState(State state) {
        List<Ticket> tickets = ticketRepository.findByState(state);
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());

        if (ticketDTO.getReporterId() != 0) {
            Tester reporter = testerRepository.findById(ticketDTO.getReporterId())
                    .orElse(null);
            ticket.setReporter(reporter);
        }

        if (ticketDTO.getFixerId() != 0) {
            Developer fixer = developerRepository.findById(ticketDTO.getFixerId())
                    .orElse(null);
            ticket.setFixer(fixer);
        }

        if (ticketDTO.getAssigneeId() != 0) {
            Developer assignee = developerRepository.findById(ticketDTO.getAssigneeId())
                    .orElse(null);
            ticket.setAssignee(assignee);
        }

        ticket.setDate(ticketDTO.getDate());
        ticket.setPriority(ticketDTO.getPriority());
        ticket.setState(State.NEW);

        List<Comment> comments = ticketDTO.getComments().stream()
                .map(commentDTO -> {
                    Comment comment = new Comment();
                    comment.setComment(commentDTO.getComment());
                    comment.setDate(commentDTO.getDate());
                    return comment;
                }).collect(Collectors.toList());

        ticket.setComments(comments);

        return ticketRepository.save(ticket);
    }

    public List<TicketResponseDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<TicketResponseDTO> getTicketById(int id) {
        return ticketRepository.findById(id).map(this::convertToDTO);
    }

    public Ticket updateTicket(int id, TicketDTO ticketDTO) {
        logger.info("Updating ticket with ID: {}", id);

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Ticket with ID {} not found", id);
                    return new RuntimeException("Ticket not found");
                });

        ticket.setTitle(ticket.getTitle());
        ticket.setDescription(ticket.getDescription());
        ticket.setState(ticketDTO.getState());

        // Fixer 설정
        if (ticketDTO.getFixerId() != 0) {
            Developer fixer = developerRepository.findById(ticketDTO.getFixerId())
                    .orElseThrow(() -> {
                        logger.error("Fixer with ID {} not found", ticketDTO.getFixerId());
                        return new RuntimeException("Fixer not found");
                    });
            ticket.setFixer(fixer);
        }

        // Assignee 설정
        else if (ticketDTO.getAssigneeId() != 0) {
            Developer assignee = developerRepository.findById(ticketDTO.getAssigneeId())
                    .orElseThrow(() -> {
                        logger.error("Assignee with ID {} not found", ticketDTO.getAssigneeId());
                        return new RuntimeException("Assignee not found");
                    });
            ticket.setAssignee(assignee);
            ticket.setState(State.ASSIGNED); // Assignee가 설정된 경우 상태를 ASSIGNED로 설정
        }

        ticket.setDate(ticketDTO.getDate());
        ticket.setPriority(ticketDTO.getPriority());

        // 기존 댓글 제거 및 새로운 댓글 추가
        List<Comment> newComments = ticketDTO.getComments().stream()
                .map(commentDTO -> {
                    Comment comment = new Comment();
                    comment.setComment(commentDTO.getComment());
                    comment.setDate(commentDTO.getDate());
                    return comment;
                }).collect(Collectors.toList());
        ticket.getComments().addAll(newComments);

        Ticket updatedTicket = ticketRepository.save(ticket);
        logger.info("Updated ticket with ID: {}", updatedTicket.getId());

        return updatedTicket;
    }

    public void deleteTicket(int id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    public TicketResponseDTO convertToDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());

        if (ticket.getAssignee() != null) {
            DeveloperDTO assignedToDeveloperDTO = new DeveloperDTO();
            assignedToDeveloperDTO.setId(ticket.getAssignee().getId());
            assignedToDeveloperDTO.setUserId(ticket.getAssignee().getUserId());
            assignedToDeveloperDTO.setPassword(ticket.getAssignee().getPassword());
            assignedToDeveloperDTO.setEmail(ticket.getAssignee().getEmail());
            assignedToDeveloperDTO.setAssignedTickets(ticket.getAssignee().getAssignedTickets().stream()
                    .map(Ticket::getId).collect(Collectors.toList()));
            assignedToDeveloperDTO.setFixedTickets(ticket.getAssignee().getFixedTickets().stream()
                    .map(Ticket::getId).collect(Collectors.toList()));
            dto.setAssignee(assignedToDeveloperDTO);
        }

        if (ticket.getFixer() != null) {
            DeveloperDTO fixedByDeveloperDTO = new DeveloperDTO();
            fixedByDeveloperDTO.setId(ticket.getFixer().getId());
            fixedByDeveloperDTO.setUserId(ticket.getFixer().getUserId());
            fixedByDeveloperDTO.setPassword(ticket.getFixer().getPassword());
            fixedByDeveloperDTO.setEmail(ticket.getFixer().getEmail());
            fixedByDeveloperDTO.setAssignedTickets(ticket.getFixer().getAssignedTickets().stream()
                    .map(Ticket::getId).collect(Collectors.toList()));
            fixedByDeveloperDTO.setFixedTickets(ticket.getFixer().getFixedTickets().stream()
                    .map(Ticket::getId).collect(Collectors.toList()));
            dto.setFixer(fixedByDeveloperDTO);
        }

        if (ticket.getReporter() != null) {
            TesterDTO reporterDTO = new TesterDTO();
            reporterDTO.setId(ticket.getReporter().getId());
            reporterDTO.setUserId(ticket.getReporter().getUserId());
            reporterDTO.setPassword(ticket.getReporter().getPassword());
            reporterDTO.setEmail(ticket.getReporter().getEmail());
            reporterDTO.setReportedTickets(ticket.getReporter().getReportedTickets().stream()
                    .map(Ticket::getId).collect(Collectors.toList()));
            dto.setReporter(reporterDTO);
        }

        dto.setDate(ticket.getDate());
        dto.setPriority(ticket.getPriority());
        dto.setState(ticket.getState());
        List<CommentDTO> commentDTOs = ticket.getComments().stream()
                .map(comment -> {
                    CommentDTO commentDTO = new CommentDTO();
                    commentDTO.setId(comment.getId());
                    commentDTO.setComment(comment.getComment());
                    commentDTO.setDate(comment.getDate());
                    return commentDTO;
                }).collect(Collectors.toList());
        dto.setComments(commentDTOs);

        return dto;
    }
}
