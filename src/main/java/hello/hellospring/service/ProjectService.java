package hello.hellospring.service;

import hello.hellospring.domain.Project;
import hello.hellospring.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    Project createProject(ProjectDTO projectDTO);
    Project updateProject(int projectId, ProjectDTO projectDTO);
    void deleteProject(int projectId);
    Project getProjectById(int projectId);
    List<Project> getAllProjects();
}
