package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.TicketService;
import hello.hellospring.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    private final DeveloperRepository developerRepository;
    private final TesterRepository testerRepository;
    private final ProjectLeaderRepository projectLeaderRepository;
    public SpringConfig(TicketRepository ticketRepository,CommentRepository commentRepository,DeveloperRepository developerRepository,TesterRepository testerRepository,ProjectLeaderRepository projectLeaderRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.developerRepository = developerRepository;
        this.testerRepository = testerRepository;
        this.projectLeaderRepository = projectLeaderRepository;
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl();
    }
}

