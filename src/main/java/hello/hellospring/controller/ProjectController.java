package hello.hellospring.controller;

import hello.hellospring.service.ProjectService;
import hello.hellospring.vo.ProjectVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 프로젝트 추가
     */
    @PostMapping("/addProject")
    public ResponseVo createProject(@RequestBody ProjectVo projectVo) {
        return projectService.insertProject(projectVo);
    }


}
