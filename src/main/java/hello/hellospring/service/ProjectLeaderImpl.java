package hello.hellospring.service;

import hello.hellospring.domain.Project;
import hello.hellospring.domain.PL;
import hello.hellospring.dto.ProjectLeaderDTO;
import hello.hellospring.repository.ProjectLeaderRepository;
import hello.hellospring.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectLeaderImpl implements ProjectLeaderService {

    @Autowired
    private ProjectLeaderRepository projectLeaderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public PL createProjectLeader(ProjectLeaderDTO projectLeaderDTO) {
        PL PL = new PL();
        PL.setUserId(projectLeaderDTO.getUserId());
        PL.setPassword(projectLeaderDTO.getPassword());
        PL.setEmail(projectLeaderDTO.getEmail());

        List<Project> projects = projectLeaderDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id).orElse(null))
                .collect(Collectors.toList());

        PL.setProjects(projects);

        PL savedPL = projectLeaderRepository.save(PL);

        for (Project project : projects) {
            project.getPLS().add(savedPL);
        }
        projectRepository.saveAll(projects);

        return savedPL;
    }

    @Override
    public List<PL> getAllProjectLeaders() {
        return projectLeaderRepository.findAll();
    }

    @Override
    public Optional<PL> getProjectLeaderById(int id) {
        return projectLeaderRepository.findById(id);
    }

    @Override
    public PL updateProjectLeader(int id, ProjectLeaderDTO projectLeaderDTO) {
        PL PL = projectLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project Leader not found"));

        PL.setUserId(projectLeaderDTO.getUserId());
        PL.setPassword(projectLeaderDTO.getPassword());
        PL.setEmail(projectLeaderDTO.getEmail());

        List<Project> projects = projectLeaderDTO.getProjectIds().stream()
                .map(projId -> projectRepository.findById(projId).orElse(null))
                .collect(Collectors.toList());

        PL.setProjects(projects);

        PL updatedPL = projectLeaderRepository.save(PL);

        for (Project project : projects) {
            project.getPLS().add(updatedPL);
        }
        projectRepository.saveAll(projects);

        return updatedPL;
    }

    @Override
    public void deleteProjectLeader(int id) {
        PL PL = projectLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project Leader not found"));
        projectLeaderRepository.delete(PL);
    }
}
