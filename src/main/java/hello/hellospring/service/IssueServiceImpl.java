package hello.hellospring.service;

import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.ResponseVo;
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
                   .date(new Date(issueVo.getDate()))
                   .fixer(issueVo.getFixer())
                   .assignee(issueVo.getAssignee())
                   .priority(issueVo.getPriority())
                   .state(issueVo.getState())
                   //.memberId(memberEntity)
                   //.projectId(projectEntity)
                   .build();
           issueRepository.save(issueEntity);
           return new ResponseVo(200,"SUCCESS");
       } catch (Exception e) {
           return new ResponseVo(99,"FAIL");
       }
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
