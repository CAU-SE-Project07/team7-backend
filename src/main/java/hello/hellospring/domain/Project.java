package hello.hellospring.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    private String projectName;
    private String projectDesc;

    @ManyToMany
    @JoinTable(
            name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    @JsonManagedReference
    private List<Developer> developers;

    @ManyToMany
    @JoinTable(
            name = "project_tester",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tester_id")
    )
    @JsonManagedReference
    private List<Tester> testers;

    @ManyToMany
    @JoinTable(
            name = "project_leader",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "leader_id")
    )
    @JsonManagedReference
    private List<PL> PLS;

    @OneToMany(mappedBy = "project")
    private List<Ticket> ticketList;
}
