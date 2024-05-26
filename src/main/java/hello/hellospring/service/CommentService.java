package hello.hellospring.service;

import hello.hellospring.entity.CommentEntity;
import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.vo.CommentVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    public ResponseVo insertComment(CommentVo commentVo) {
        try {
            if(ObjectUtils.isEmpty(commentVo)) {
                return null;
            }
            /** 코멘트를 등록하려는 프로젝트 엔티티 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(commentVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(11,"프로젝트가 존재하지 않습니다.");
            }
            /** 코멘트를 등록하려는 사용자 엔티티 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(commentVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(12,"사용자가 존재하지 않습니다.");
            }
            /** 코멘트를 등록하려는 이슈 엔티티 조회 */
            IssueEntity issueEntity = issueRepository.findByTitle(commentVo.getTitle());
            if(issueEntity == null) {
                return new ResponseVo(13,"이슈가 존재하지 않습니다.");
            }
            /** 코멘트 데이터 insert */
            CommentEntity commentEntity = CommentEntity.builder()
                    .content(commentVo.getContent())
                    .date(commentVo.getDate())
                    .projectId(projectEntity.getProjectId())
                    .memberId(memberEntity.getMemberId())
                    .issueId(issueEntity.getIssueId())
                    .build();
            commentRepository.save(commentEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }
}
