package hello.hellospring.service;

import hello.hellospring.domain.*;
import hello.hellospring.domain.enums.State;
import hello.hellospring.dto.*;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.ProjectRepository;
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
    @Autowired
    private ProjectRepository projectRepository;

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

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setState(ticketDTO.getState());

        // Reporter 설정
        if (ticketDTO.getReporterId() != 0) {
            Tester reporter = testerRepository.findById(ticketDTO.getReporterId())
                    .orElseThrow(() -> new RuntimeException("Reporter not found"));
            ticket.setReporter(reporter);
        }

        // Fixer 설정
        ticket.setFixer(null);

        // Assignee 설정
        ticket.setAssignee(null);

        ticket.setDate(ticketDTO.getDate());
        ticket.setPriority(ticketDTO.getPriority());

        // Project 설정
        if (ticketDTO.getProjectId() != 0) {
            Project project = projectRepository.findById(ticketDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            ticket.setProject(project);
        }

        // 먼저 Ticket을 저장하여 ID를 생성
        Ticket savedTicket = ticketRepository.save(ticket);

        // 새로운 댓글 추가 및 ticket 설정
        List<Comment> comments = ticketDTO.getComments().stream()
                .map(commentDTO -> {
                    Comment comment = new Comment();
                    comment.setComment(commentDTO.getComment());
                    comment.setDate(commentDTO.getDate());
                    comment.setTicket(savedTicket); // 댓글에 ticket 설정
                    return comment;
                }).collect(Collectors.toList());
        savedTicket.getComments().addAll(comments);

        // 다시 저장하여 Comments와의 관계 설정
        return ticketRepository.save(savedTicket);
    }

    @Override
    public List<Ticket> getAllTicketsOfProjectId(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return ticketRepository.findByProject(project);
    }

    @Override
    public Ticket getTicketByIdAndProjectId(int id, int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return ticketRepository.findByIdAndProject(id, project)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public Ticket updateTicket(int id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setTitle(ticketDTO.getTitle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setState(ticketDTO.getState());

        // Reporter 설정
        if (ticketDTO.getReporterId() != 0) {
            Tester reporter = testerRepository.findById(ticketDTO.getReporterId())
                    .orElseThrow(() -> new RuntimeException("Reporter not found"));
            ticket.setReporter(reporter);
        }

        // Fixer 설정
        if (ticketDTO.getFixerId() != 0) {
            Developer fixer = developerRepository.findById(ticketDTO.getFixerId())
                    .orElseThrow(() -> new RuntimeException("Fixer not found"));
            ticket.setFixer(fixer);
        } else {
            ticket.setFixer(null);
        }

        // Assignee 설정
        if (ticketDTO.getAssigneeId() != 0) {
            Developer assignee = developerRepository.findById(ticketDTO.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            ticket.setAssignee(assignee);
        } else {
            ticket.setAssignee(null);
        }

        ticket.setDate(ticketDTO.getDate());
        ticket.setPriority(ticketDTO.getPriority());

        // Project 설정
        if (ticketDTO.getProjectId() != 0) {
            Project project = projectRepository.findById(ticketDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            ticket.setProject(project);
        }

        // 새로운 댓글 추가 및 기존 댓글 업데이트
        List<Comment> comments = ticketDTO.getComments().stream()
                .map(commentDTO -> {
                    Comment comment = new Comment();
                    comment.setComment(commentDTO.getComment());
                    comment.setDate(commentDTO.getDate());
                    comment.setTicket(ticket); // 댓글에 ticket 설정
                    return comment;
                }).collect(Collectors.toList());
        ticket.getComments().clear();
        ticket.getComments().addAll(comments);

        return ticketRepository.save(ticket);
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
                    .map(Ticket::getId).collect(Collectors.toList())); //
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
                    commentDTO.setTicketId(comment.getTicket().getId());
                    return commentDTO;
                }).collect(Collectors.toList());
        dto.setComments(commentDTOs);

        return dto;
    }
}
