package hello.hellospring;

import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.TesterRepository;
import hello.hellospring.repository.TicketRepository;
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
    public SpringConfig(TicketRepository ticketRepository,CommentRepository commentRepository,DeveloperRepository developerRepository,TesterRepository testerRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
        this.developerRepository = developerRepository;
        this.testerRepository = testerRepository;
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl();
    }
}

