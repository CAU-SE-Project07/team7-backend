package hello.hellospring.service;

import hello.hellospring.domain.ProjectLeader;
import hello.hellospring.dto.ProjectLeaderDTO;
import hello.hellospring.repository.ProjectLeaderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectLeaderImpl implements ProjectLeaderService {

    @Autowired
    private ProjectLeaderRepository projectLeaderRepository;

    public ProjectLeader createProjectLeader(ProjectLeaderDTO projectLeaderDTO) {
        ProjectLeader projectLeader = new ProjectLeader();
        projectLeader.setUserId(projectLeaderDTO.getUserId());
        projectLeader.setPassword(projectLeaderDTO.getPassword());
        projectLeader.setEmail(projectLeaderDTO.getEmail());

        return projectLeaderRepository.save(projectLeader);
    }

    public List<ProjectLeader> getAllProjectLeaders() {
        return projectLeaderRepository.findAll();
    }

    public Optional<ProjectLeader> getProjectLeaderById(int id) {
        return projectLeaderRepository.findById(id);
    }

    public ProjectLeader updateProjectLeader(int id, ProjectLeaderDTO projectLeaderDTO) {
        ProjectLeader projectLeader = projectLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProjectLeader not found"));

        projectLeader.setUserId(projectLeaderDTO.getUserId());
        projectLeader.setPassword(projectLeaderDTO.getPassword());
        projectLeader.setEmail(projectLeaderDTO.getEmail());

        return projectLeaderRepository.save(projectLeader);
    }

    public void deleteProjectLeader(int id) {
        ProjectLeader projectLeader = projectLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProjectLeader not found"));
        projectLeaderRepository.delete(projectLeader);
    }
}

