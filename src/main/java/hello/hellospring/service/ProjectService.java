package hello.hellospring.service;

import hello.hellospring.vo.ProjectVo;
import hello.hellospring.vo.ResponseVo;

public interface ProjectService {
    ResponseVo insertProject(ProjectVo projectVo);
}
