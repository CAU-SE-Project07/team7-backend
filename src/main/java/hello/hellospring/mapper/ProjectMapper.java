package hello.hellospring.mapper;

import hello.hellospring.vo.ProjectReqVo;
import hello.hellospring.vo.ProjectResVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    void addProject(ProjectReqVo projectReqVo);
    void updateProject(ProjectReqVo projectReqVo);
    List<ProjectResVo> selectProjects(ProjectReqVo projectReqVo);
}
