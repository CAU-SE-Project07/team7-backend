package hello.hellospring.dto;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TicketDTO {
    private int id;
    private String title;
    private String description;
    private int reporterId;  // Reporter ID만 포함
    private Date date;
    private int fixerId;     // Fixer ID만 포함
    private int assigneeId;  // Assignee ID만 포함
    private Priority priority;
    private State state;
    private List<CommentDTO> comments = new ArrayList<>();
    private int projectId;

    // Getters and Setters
}