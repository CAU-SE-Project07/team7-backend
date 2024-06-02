package hello.hellospring.service;

import hello.hellospring.Vo.ProjectVo;
import hello.hellospring.Vo.ResponseVo;

public interface ProjectService {
    ResponseVo insertProject(ProjectVo projectVo);
    ResponseVo updateProject(ProjectVo projectVo);
}
