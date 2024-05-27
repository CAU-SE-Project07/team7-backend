package hello.hellospring.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Tester extends Member {

    @OneToMany(mappedBy = "reporter")
    private List<Ticket> reportedTickets;

    @ManyToMany(mappedBy = "testers")
    @JsonBackReference
    private List<Project> projects;
}