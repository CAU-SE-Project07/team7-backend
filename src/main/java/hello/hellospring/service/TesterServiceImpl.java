package hello.hellospring.service;

import hello.hellospring.domain.Project;
import hello.hellospring.domain.Tester;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.TesterDTO;
import hello.hellospring.repository.ProjectRepository;
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

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Tester createTester(TesterDTO testerDTO) {
        Tester tester = new Tester();
        tester.setUserId(testerDTO.getUserId());
        tester.setPassword(testerDTO.getPassword());
        tester.setEmail(testerDTO.getEmail());

        List<Ticket> reportedTickets = testerDTO.getReportedTickets().stream()
                .map(id -> ticketRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        tester.setReportedTickets(reportedTickets);

        List<Project> projects = testerDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id).orElse(null))
                .collect(Collectors.toList());

        for (Project project : projects) {
            if (project.getTesters().contains(tester)) {
                throw new RuntimeException("Tester already exists in project: " + project.getProjectName());
            }
        }
        for (Project project : projects) {
            if (!project.getTesters().contains(tester)) {
                project.getTesters().add(tester);
            }
        }

        tester.setProjects(projects);

        Tester savedTester = testerRepository.save(tester);
        projectRepository.saveAll(projects);

        return savedTester;
    }

    @Override
    public List<Tester> getAllTesters() {
        return testerRepository.findAll();
    }

    @Override
    public Optional<Tester> getTesterById(int id) {
        return testerRepository.findById(id);
    }

    @Override
    public Tester updateTester(int testerid, TesterDTO testerDTO) {
        Tester tester = testerRepository.findById(testerid)
                .orElseThrow(() -> new RuntimeException("Tester not found"));

        tester.setUserId(testerDTO.getUserId());
        tester.setPassword(testerDTO.getPassword());
        tester.setEmail(testerDTO.getEmail());

        List<Ticket> reportedTickets = testerDTO.getReportedTickets().stream()
                .map(ticketId -> ticketRepository.findById(ticketId).orElse(null))
                .collect(Collectors.toList());
        tester.setReportedTickets(reportedTickets);

        List<Project> projects = testerDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id).orElse(null))
                .collect(Collectors.toList());

        for (Project project : projects) {
            if (project.getTesters().contains(tester)) {
                throw new RuntimeException("Tester already exists in project: " + project.getProjectName());
            }
        }
        for (Project project : projects) {
            if (!project.getTesters().contains(tester)) {
                project.getTesters().add(tester);
            }
        }

        tester.setProjects(projects);

        Tester updatedTester = testerRepository.save(tester);
        projectRepository.saveAll(projects);

        return updatedTester;
    }

    @Override
    public void deleteTester(int id) {
        Tester tester = testerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tester not found"));
        testerRepository.delete(tester);
    }
}
