package hello.hellospring.service;

import hello.hellospring.domain.Tester;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.TesterDTO;
import hello.hellospring.repository.TesterRepository;
import hello.hellospring.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TesterServiceImpl implements TesterService {

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Tester createTester(TesterDTO testerDTO) {
        Tester tester = new Tester();
        tester.setUserId(testerDTO.getUserId());
        tester.setPassword(testerDTO.getPassword());
        tester.setEmail(testerDTO.getEmail());

        if (testerDTO.getReportedTickets() != null) {
            List<Ticket> reportedTickets = testerDTO.getReportedTickets().stream()
                    .map(id -> ticketRepository.findById(id).orElse(null))
                    .collect(Collectors.toList());
            tester.setReportedTickets(reportedTickets);
        }

        return testerRepository.save(tester);
    }

    public List<Tester> getAllTesters() {
        return testerRepository.findAll();
    }

    public Optional<Tester> getTesterById(int id) {
        return testerRepository.findById(id);
    }

    public Tester updateTester(int id, TesterDTO testerDTO) {
        Tester tester = testerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tester not found"));

        tester.setUserId(testerDTO.getUserId());
        tester.setPassword(testerDTO.getPassword());
        tester.setEmail(testerDTO.getEmail());

        if (testerDTO.getReportedTickets() != null) {
            List<Ticket> reportedTickets = testerDTO.getReportedTickets().stream()
                    .map(ticketId -> ticketRepository.findById(ticketId).orElse(null))
                    .collect(Collectors.toList());
            tester.setReportedTickets(reportedTickets);
        }

        return testerRepository.save(tester);
    }

    public void deleteTester(int id) {
        Tester tester = testerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tester not found"));
        testerRepository.delete(tester);
    }
}