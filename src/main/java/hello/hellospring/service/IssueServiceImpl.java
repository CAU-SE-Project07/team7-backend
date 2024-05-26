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

import java.text.SimpleDateFormat;
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
               return new ResponseVo(11,"제목이 중복됩니다.");
           }
           /** 우선순위 처리 */
           if(issueVo.getPriority() == null || issueVo.getPriority().equals("")) {
               issueVo.setPriority("major");
           }
           /** 등록하려는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(12,"프로젝트가 존재하지 않습니다.");
            }
            /** 사용자 찾기 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(13,"사용자가 존재하지 않습니다.");
            }
           IssueEntity issueEntity = IssueEntity.builder()
                   .title(issueVo.getTitle())
                   .description(issueVo.getDescription())
                   .reporter(issueVo.getReporter())
                   .date(issueVo.getDate())
                   .fixer(issueVo.getFixer())
                   .assignee(issueVo.getAssignee())
                   .priority(issueVo.getPriority())
                   .state(issueVo.getState())
                   .projectId(projectEntity.getProjectId())
                   .memberId(memberEntity.getMemberId())
                   .build();
           issueRepository.save(issueEntity);
           return new ResponseVo(200,"SUCCESS");
       } catch (Exception e) {
           return new ResponseVo(99,"FAIL");
       }
    }

    @Override
    public ResponseVo updateIssue(IssueVo issueVo) {
        if(ObjectUtils.isEmpty(issueVo)) {
            return null;
        }
        try {
            /** 현재 변경하려는 이슈에 해당하는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(11, "프로젝트가 존재하지 않습니다.");
            }
            /** 현재 변경하려는 이슈에 해당하는 사용자 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(12, "사용자가 존재하지 않습니다.");
            }
            /** 현재 변경하려는 기존 이슈 조회 */
            IssueEntity issueEntity = issueRepository.findByTitle(issueVo.getTitle());
            if(issueEntity == null) {
                return new ResponseVo(13,"이슈가 존재하지 않습니다.");
            }
            /** reporter 변경 시 존재하는 사용자인지 체크 */
            MemberEntity chkReporter = memberRepository.findByUserId(issueVo.getReporter());
            if(chkReporter == null) {
                return new ResponseVo(14, "레포터 사용자가 존재하지 않습니다.");
            }

            /** 이슈의 상태 및 reporter 변경 > test1 사용자 */
            IssueEntity updateIssue = IssueEntity.builder()
                    .issueId(issueEntity.getIssueId())
                    .title(issueVo.getTitle())
                    .description(issueVo.getDescription())
                    .reporter(issueVo.getReporter())
                    .date(issueVo.getDate())
                    .fixer(issueVo.getFixer())
                    .assignee(issueVo.getAssignee())
                    .priority(issueVo.getPriority())
                    .state(issueVo.getState())
                    .projectId(projectEntity.getProjectId())
                    .memberId(memberEntity.getMemberId())
                    .build();
            issueRepository.save(updateIssue);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(9,"FAIL");
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