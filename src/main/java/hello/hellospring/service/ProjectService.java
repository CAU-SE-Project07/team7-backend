package hello.hellospring.service;

import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.ProjectMapper;
import hello.hellospring.vo.ProjectReqVo;
import hello.hellospring.vo.ProjectResVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    /**
     * MyBatis - 프로젝트 추가
     * */
    public ResponseVo addProject(ProjectReqVo projectReqVo) {
        if(ObjectUtils.isEmpty(projectReqVo)) {
            return null;
        }
        try {
            projectMapper.addProject(projectReqVo);
            /** 위에서 추가된 프로젝트 조회 */
            List<ProjectResVo> list = projectMapper.selectProjects(projectReqVo);
            return new ResponseVo(200,"SUCCESS", list);
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }

    /**
     * MyBatis - 프로젝트 변경
     * */
    public ResponseVo updateProject(ProjectReqVo projectReqVo) {
        if(ObjectUtils.isEmpty(projectReqVo)) {
            return null;
        }
        try {
            List<ProjectResVo> projectResVo = projectMapper.selectProjects(projectReqVo);
            if(projectReqVo.getProjectId() == 0 || projectResVo.size() == 0) {
                return new ResponseVo(11,"프로젝트 아이디가 존재하지 않습니다.");
            }
            projectMapper.updateProject(projectReqVo);
            /** 위에서 추가된 프로젝트 조회 */
            List<ProjectResVo> list = projectMapper.selectProjects(projectReqVo);
            return new ResponseVo(200,"SUCCESS", list);
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }

}
