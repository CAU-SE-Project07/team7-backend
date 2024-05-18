package hello.hellospring;

import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.TicketRepository;
import hello.hellospring.service.TicketService;
import hello.hellospring.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;
    public SpringConfig(TicketRepository ticketRepository,CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl(ticketRepository, commentRepository);
    }
}

