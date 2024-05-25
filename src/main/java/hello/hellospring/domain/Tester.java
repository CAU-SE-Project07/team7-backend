package hello.hellospring.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Tester extends Member{
    @OneToMany(mappedBy = "reporter")
    private List<Ticket> reportedTickets;
}