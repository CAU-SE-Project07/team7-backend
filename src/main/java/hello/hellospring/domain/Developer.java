package hello.hellospring.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Developer extends Member{
    @OneToMany(mappedBy = "assignee")
    private List<Ticket> assignedTickets;

    @OneToMany(mappedBy = "fixer")
    private List<Ticket> fixedTickets;
}