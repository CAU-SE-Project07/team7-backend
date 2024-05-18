package hello.hellospring;

import hello.hellospring.repository.TicketRepository;
import hello.hellospring.service.TicketService;
import hello.hellospring.service.TicketServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final TicketRepository ticketRepository;

    public SpringConfig(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl(ticketRepository);
    }
}

