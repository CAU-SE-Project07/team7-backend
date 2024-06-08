package hello.hellospring.repository;

import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.ProjectVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    ProjectEntity findByProjectNm(String projectNm);
    ProjectEntity findByProjectId(Integer projectId);
}
