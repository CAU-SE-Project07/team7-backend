package hello.hellospring.service;

import hello.hellospring.domain.PL;
import hello.hellospring.dto.ProjectLeaderDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectLeaderService {
    PL createProjectLeader(ProjectLeaderDTO projectLeaderDTO);
    List<PL> getAllProjectLeaders();
    Optional<PL> getProjectLeaderById(int id);
    PL updateProjectLeader(int id, ProjectLeaderDTO projectLeaderDTO);
    void deleteProjectLeader(int id);
}
