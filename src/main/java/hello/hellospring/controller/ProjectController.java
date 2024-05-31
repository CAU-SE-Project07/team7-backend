package hello.hellospring.controller;

import hello.hellospring.service.ProjectService;
import hello.hellospring.vo.ProjectReqVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * MyBatis - 프로젝트 추가
     * */
    @PostMapping("/01")
    public ResponseVo addProject(@RequestBody ProjectReqVo projectReqVo) {
        return projectService.addProject(projectReqVo);
    }

    /**
     * MyBatis - 프로젝트 변경
     * */
    @PostMapping("/02")
    public ResponseVo updateProject(@RequestBody ProjectReqVo projectReqVo){
        return projectService.updateProject(projectReqVo);
    }

}
