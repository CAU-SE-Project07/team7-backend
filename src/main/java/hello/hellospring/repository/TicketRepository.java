package hello.hellospring.repository;

import hello.hellospring.domain.Priority;
import hello.hellospring.domain.State;
import hello.hellospring.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByTitle(String title);
    List<Ticket> findByDescription(String description);
    List<Ticket> findByReporter(String reporter);
    List<Ticket> findByDate(Date date);
    List<Ticket> findByFixer(String fixer);
    List<Ticket> findByAssignee(String assignee);
    List<Ticket>findByPriority(Priority priority);
    List<Ticket>findByState(State state);
}
