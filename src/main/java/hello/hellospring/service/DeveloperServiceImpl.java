package hello.hellospring.service;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.Project;
import hello.hellospring.domain.Ticket;
import hello.hellospring.dto.DeveloperDTO;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.ProjectRepository;
import hello.hellospring.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
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

        List<Project> projects = developerDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        for (Project project : projects) {
            if (project.getDevelopers().contains(developer)) {
                throw new RuntimeException("Developer already exists in project: " + project.getProjectName());
            }
        }
        for (Project project : projects) {
            if (!project.getDevelopers().contains(developer)) {
                project.getDevelopers().add(developer);
            }
        }

        developer.setProjects(projects);

        Developer savedDeveloper = developerRepository.save(developer);
        projectRepository.saveAll(projects);

        return savedDeveloper;
    }

    @Override
    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @Override
    public Optional<Developer> getDeveloperById(int id) {
        return developerRepository.findById(id);
    }

    @Override
    public Developer updateDeveloper(int devid, DeveloperDTO developerDTO) {
        Developer developer = developerRepository.findById(devid)
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

        List<Project> projects = developerDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        for (Project project : projects) {
            if (project.getDevelopers().contains(developer)) {
                throw new RuntimeException("Developer already exists in project: " + project.getProjectName());
            }
        }
        for (Project project : projects) {
            if (!project.getDevelopers().contains(developer)) {
                project.getDevelopers().add(developer);
            }
        }

        developer.setProjects(projects);

        Developer updatedDeveloper = developerRepository.save(developer);
        projectRepository.saveAll(projects);

        return updatedDeveloper;
    }

    @Override
    public void deleteDeveloper(int id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Developer not found"));
        developerRepository.delete(developer);
    }
}