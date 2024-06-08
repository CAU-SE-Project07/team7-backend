package hello.hellospring.service;

import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.ProjectVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseVo insertProject(ProjectVo projectVo) {
        try {
            if(ObjectUtils.isEmpty(projectVo)) {
                return null;
            }
            /** 프로젝트명 중복 체크 */
//            ProjectEntity isExsitedProjectEntity = projectRepository.findByProjectNm(projectVo.getProjectNm());
//            if(isExsitedProjectEntity != null) {
//                return new ResponseVo(11,"Project Name is duplicated.");
//            }
            /** 프로젝트 기본키 : projectId => 고유값 처리 */
            int projectId = 1;
//            int count = projectRepository.findAll().size();
//            if(count > 0) {
//                projectId = count + 1;
//            }
            ProjectEntity projectEntity = ProjectEntity.builder()
                    .projectId(projectId)
                    .projectNm(projectVo.getProjectNm())
                    .projectDesc(projectVo.getProjectDesc())
                    .build();
            projectRepository.save(projectEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }
    @Override
    public ResponseVo updateProject(ProjectVo projectVo)
    {
        try {
            int projectId = 1;
            ProjectEntity projectEntity = projectRepository.findByProjectId(projectId);
            ProjectEntity updatedProject = ProjectEntity.builder()
                    .projectId(projectId)
                    .projectNm(projectVo.getProjectNm())
                    .projectDesc(projectVo.getProjectDesc())
                    .build();
            projectRepository.save(updatedProject);
            return new ResponseVo(200, "SUCCESS");
        }catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }
}
