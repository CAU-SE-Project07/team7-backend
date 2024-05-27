package hello.hellospring.domain;

import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Tester reporter;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Developer assignee;

    @ManyToOne
    @JoinColumn(name = "fixer_id")
    private Developer fixer;

    private Date date;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
