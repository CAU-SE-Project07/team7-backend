package hello.hellospring.service;

import hello.hellospring.domain.ProjectLeader;
import hello.hellospring.dto.ProjectLeaderDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectLeaderService {
    public ProjectLeader createProjectLeader(ProjectLeaderDTO projectLeaderDTO);
    public List<ProjectLeader> getAllProjectLeaders();
    public Optional<ProjectLeader> getProjectLeaderById(int id);
    public ProjectLeader updateProjectLeader(int id, ProjectLeaderDTO projectLeaderDTO);
    public void deleteProjectLeader(int id);
}
