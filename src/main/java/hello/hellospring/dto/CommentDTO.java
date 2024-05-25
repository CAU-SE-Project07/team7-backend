package hello.hellospring.dto;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private int id;
    private String comment;
    private Date date;
    private int ticketId;

    // Getters and Setters
}