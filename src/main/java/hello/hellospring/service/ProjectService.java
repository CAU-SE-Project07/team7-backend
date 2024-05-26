package hello.hellospring.service;

import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.vo.ProjectVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ResponseVo insertProject(ProjectVo projectVo) {
        try {
            if(ObjectUtils.isEmpty(projectVo)) {
                return null;
            }
            /** 프로젝트명 중복 체크 */
            ProjectEntity isExsitedProjectEntity = projectRepository.findByProjectNm(projectVo.getProjectNm());
            if(isExsitedProjectEntity != null) {
                return new ResponseVo(11,"Project Name is duplicated.");
            }
            ProjectEntity projectEntity = ProjectEntity.builder()
                    .projectNm(projectVo.getProjectNm())
                    .projectDesc(projectVo.getProjectDesc())
                    .build();
            projectRepository.save(projectEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }
}
