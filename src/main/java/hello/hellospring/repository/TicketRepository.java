package hello.hellospring.repository;

import hello.hellospring.domain.Project;
import hello.hellospring.domain.enums.Priority;
import hello.hellospring.domain.enums.State;
import hello.hellospring.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT t FROM Ticket t WHERE t.assignee.id = :developerId")
    List<Ticket> findByAssignedToDeveloperId(@Param("developerId") int developerId);

    @Query("SELECT t FROM Ticket t WHERE t.reporter.id = :reporterId AND t.state = 'FIXED'")
    List<Ticket> findFixedTicketsByReporterId(@Param("reporterId") int reporterId);

    List<Ticket> findByState(State state);

    List<Ticket> findByProject(Project project);
    Optional<Ticket> findByIdAndProject(int id, Project project);
}
