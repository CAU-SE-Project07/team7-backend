package hello.hellospring.service;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.DeveloperDTO;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeveloperServiceImpl implements DeveloperService{
    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Developer createDeveloper(DeveloperDTO developerDTO) {
        Developer developer = new Developer();
        developer.setUserId(developerDTO.getUserId());
        developer.setPassword(developerDTO.getPassword());
        developer.setEmail(developerDTO.getEmail());

        List<Ticket> assignedTickets = developerDTO.getAssignedTickets().stream()
                .map(id -> ticketRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        developer.setAssignedTickets(assignedTickets);

        List<Ticket> fixedTickets = developerDTO.getFixedTickets().stream()
                .map(id -> ticketRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        developer.setFixedTickets(fixedTickets);

        return developerRepository.save(developer);
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Optional<Developer> getDeveloperById(int id) {
        return developerRepository.findById(id);
    }

    public Developer updateDeveloper(int id, DeveloperDTO developerDTO) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Developer not found"));

        developer.setUserId(developerDTO.getUserId());
        developer.setPassword(developerDTO.getPassword());
        developer.setEmail(developerDTO.getEmail());

        List<Ticket> assignedTickets = developerDTO.getAssignedTickets().stream()
                .map(ticketId -> ticketRepository.findById(ticketId).orElse(null))
                .collect(Collectors.toList());
        developer.setAssignedTickets(assignedTickets);

        List<Ticket> fixedTickets = developerDTO.getFixedTickets().stream()
                .map(ticketId -> ticketRepository.findById(ticketId).orElse(null))
                .collect(Collectors.toList());
        developer.setFixedTickets(fixedTickets);

        return developerRepository.save(developer);
    }

    public void deleteDeveloper(int id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Developer not found"));
        developerRepository.delete(developer);
    }
}
