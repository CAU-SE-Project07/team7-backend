package hello.hellospring.service;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.IssueVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository, CommentRepository commentRepository, MemberRepository memberRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseVo insertIssue(IssueVo issueVo) {
        try {
           if(ObjectUtils.isEmpty(issueVo)) {
               return null;
           }
           /** 등록하려는 이슈 제목 중복 체크 */
           IssueEntity isExistedIssueEntity = issueRepository.findByTitle(issueVo.getTitle());
           if(isExistedIssueEntity != null) {
               return new ResponseVo(11,"Title is duplicated.");
           }
            /** 이슈 기본키 : issueId => 고유값 처리 */
            int issueId = 1;
            int count = issueRepository.findAll().size();
            if(count > 0) {
                issueId = count + 1;
            }
           /** 우선순위 처리 */
           if(issueVo.getPriority() == null || issueVo.getPriority().equals("")) {
               issueVo.setPriority("MAJOR");
           }
           /** 등록하려는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueVo.getProjectNm());
            /** 사용자 찾기 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueVo.getUserId());
           IssueEntity issueEntity = IssueEntity.builder()
                   .issueId(issueId)
                   .title(issueVo.getTitle())
                   .description(issueVo.getDescription())
                   .reporter(issueVo.getReporter())
                   .date(issueVo.getDate())
                   .fixer(issueVo.getFixer())
                   .assignee(issueVo.getAssignee())
                   .priority(issueVo.getPriority())
                   .state(issueVo.getState())
                   .memberId(memberEntity)
                   .projectId(projectEntity)
                   .build();
           issueRepository.save(issueEntity);
           return new ResponseVo(200,"SUCCESS");
       } catch (Exception e) {
           return new ResponseVo(99,"FAIL");
       }
    }

    @Override
    public ResponseVo updateIssue(IssueVo issueVo)
    {
        /** 기존 이슈 긁어오기 */
        IssueEntity origEntity = issueRepository.findByTitle(issueVo.getTitle());
        /**
         * 이슈에 대해 dev1을 담당자(assignee)로 지정하며,
         * 코멘트에 적절한 메시지를 추가함.
         * 이후 시스템은 해당 이슈의 상태를 assigned로 변경함
         * 즉, assignee가 바뀌면 state를 assigned로 바꿈**/
        String changedState = issueVo.getState();
        String changedAssignee = origEntity.getAssignee();
        if(issueVo.getAssignee()!=origEntity.getAssignee())
        {
            changedState = "ASSIGNED";
            changedAssignee = issueVo.getAssignee();
        }
        /** 전에 Assigned였고 이번에 fixed로 바뀌었으면 assignee를 fixer로 지정
         * 이외엔 원래의 fixer로 지정**/
        String chagnedFixer = origEntity.getFixer();
        if(origEntity.getState()!="ASSIGNED"&&issueVo.getState()=="FIXED")
        {
            chagnedFixer = issueVo.getAssignee();
        }

        IssueEntity updateIssue = IssueEntity.builder().
                issueId(origEntity.getIssueId()).
                title(origEntity.getTitle()).
                description(origEntity.getDescription()).
                reporter(origEntity.getReporter()).
                date(issueVo.getDate()).
                fixer(chagnedFixer).
                assignee(changedAssignee).
                priority(issueVo.getPriority()).
                state(changedState).
                memberId(origEntity.getMemberId()).
                projectId(origEntity.getProjectId()).
                build();
        issueRepository.save(updateIssue);
        return new ResponseVo(200,"SUCCESS");


    }
    @Override
    public ResponseVo<IssueVo> getListByUserIdAndState(String userId, String state) {
        /** 사용자 테이블 - 현재 로그인 한 사용자 정보 조회 - 기본키 값 가져오기 위해 */
        MemberEntity memberEntity = memberRepository.findByUserId(userId);
        List<IssueEntity> issueEntities = issueRepository.findByMemberId(memberEntity);

        /** 순서 => 중간 관리자 로그인 시 본인에게(Assignee) 할당된 이슈 먼저 조회 => 상태 값 검색해서 조회 */
        /** 1. 현재 로그인 한 중간 관리자에 할당된 모든 이슈 조회 API */

        List<IssueVo> resultList = new ArrayList<>();
        issueEntities.stream().map(e -> {
            IssueVo issueVo = IssueVo.builder()
                    .issueId(e.getIssueId())
                    .title(e.getTitle())
                    .description(e.getDescription())
                    .reporter(e.getReporter())
                    .date(e.getDate().toString())
                    .fixer(e.getFixer())
                    .assignee(e.getAssignee())
                    .priority(e.getPriority())
                    .state(e.getState())
//                    .userId(e.)
                    .build();
            resultList.add(issueVo);
            return issueVo;
        }).collect(Collectors.toList());
        return new ResponseVo<IssueVo>(200,"SUCCESS", resultList);
    }

}
