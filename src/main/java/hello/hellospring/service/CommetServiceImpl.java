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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@Transactional
public class CommetServiceImpl implements CommentService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public CommetServiceImpl(IssueRepository issueRepository, CommentRepository commentRepository, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public ResponseVo insertComment(CommentVo commentVo) {
        try {
            if(ObjectUtils.isEmpty(commentVo)) {
                return null;
            }
            /** 코멘트 기본키 : commentId => 고유값 처리 */
            int commentId = 1;
            int count = commentRepository.findAll().size();
            if(count > 0) {
                commentId = count + 1;
            }
            /** 코멘트를 등록하려는 프로젝트 엔티티 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(commentVo.getProjectNm());
            /** 코멘트를 등록하려는 사용자 엔티티 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(commentVo.getUserId());
            /** 코멘트를 등록하려는 이슈 엔티티 조회 */
            IssueEntity issueEntity = issueRepository.findByTitle(commentVo.getTitle());
            /** 코멘트 데이터 insert */
            CommentEntity commentEntity = CommentEntity.builder()
                    .commentId(commentId)
                    .content(commentVo.getContent())
                    .date(new Date(commentVo.getDate()))
                    .projectId(projectEntity)
                    .memberId(memberEntity)
                    .issueId(issueEntity)
                    .build();
            commentRepository.save(commentEntity);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99, "FAIL");
        }
    }
}
