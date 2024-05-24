package hello.hellospring.controller;

import hello.hellospring.Vo.ProjectVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.ProjectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 프로젝트 추가
     */
    @PostMapping("/addProject")
    public ResponseVo createProject(@RequestBody ProjectVo projectVo) {
        return projectService.insertProject(projectVo);
    }

}
