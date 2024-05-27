package hello.hellospring.dto;

import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class TicketResponseDTO {
    private int id;
    private String title;
    private String description;
    private DeveloperDTO fixer;
    private DeveloperDTO assignee;
    private TesterDTO reporter;
    private Date date;
    private Priority priority;
    private State state;
    private List<CommentDTO> comments;

    // Getters and Setters
}
