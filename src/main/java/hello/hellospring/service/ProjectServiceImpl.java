package hello.hellospring.service;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.Project;
import hello.hellospring.domain.Tester;
import hello.hellospring.dto.ProjectDTO;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.ProjectRepository;
import hello.hellospring.repository.TesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TesterRepository testerRepository;

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectDesc(projectDTO.getProjectDesc());

        List<Developer> developers = projectDTO.getDeveloperIds().stream()
                .map(id -> developerRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        project.setDevelopers(developers);

        List<Tester> testers = projectDTO.getTesterIds().stream()
                .map(id -> testerRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        project.setTesters(testers);

        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(int projectId, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProjectName(projectDTO.getProjectName());
        project.setProjectDesc(projectDTO.getProjectDesc());

        List<Developer> developers = projectDTO.getDeveloperIds().stream()
                .map(id -> developerRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        project.setDevelopers(developers);

        List<Tester> testers = projectDTO.getTesterIds().stream()
                .map(id -> testerRepository.findById(id).orElse(null))
                .collect(Collectors.toList());
        project.setTesters(testers);

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.delete(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectById(int projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
