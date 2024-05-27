package hello.hellospring.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Comment {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    private String comment;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

}
