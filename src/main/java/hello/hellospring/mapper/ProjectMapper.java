package hello.hellospring.mapper;

import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectMapper {
    List<ProjectVo> selectAll();
}
