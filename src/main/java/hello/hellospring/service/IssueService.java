package hello.hellospring.service;

import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import hello.hellospring.entity.ProjectEntity;
import hello.hellospring.mapper.IssueMapper;
import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final IssueMapper issueMapper;

    public ResponseVo insertIssue(IssueResVo issueResVo) {
        try {
           if(ObjectUtils.isEmpty(issueResVo)) {
               return null;
           }
           /** 등록하려는 이슈 제목 중복 체크 */
           IssueEntity isExistedIssueEntity = issueRepository.findByTitle(issueResVo.getTitle());
           if(isExistedIssueEntity != null) {
               return new ResponseVo(11,"제목이 중복됩니다.");
           }
           /** 우선순위 처리 */
           if(issueResVo.getPriority() == null || issueResVo.getPriority().equals("")) {
               issueResVo.setPriority("major");
           }
           /** 등록하려는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueResVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(12,"프로젝트가 존재하지 않습니다.");
            }
            /** 사용자 찾기 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueResVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(13,"사용자가 존재하지 않습니다.");
            }
           IssueEntity issueEntity = IssueEntity.builder()
                   .title(issueResVo.getTitle())
                   .description(issueResVo.getDescription())
                   .reporter(issueResVo.getReporter())
                   .date(issueResVo.getDate())
                   .fixer(issueResVo.getFixer())
                   .assignee(issueResVo.getAssignee())
                   .priority(issueResVo.getPriority())
                   .state(issueResVo.getState())
                   .projectId(projectEntity.getProjectId())
                   .memberId(memberEntity.getMemberId())
                   .build();
           issueRepository.save(issueEntity);
           return new ResponseVo(200,"SUCCESS");
       } catch (Exception e) {
           return new ResponseVo(99,"FAIL");
       }
    }

    public ResponseVo updateIssue(IssueResVo issueResVo) {
        if(ObjectUtils.isEmpty(issueResVo)) {
            return null;
        }
        try {
            /** 현재 변경하려는 이슈에 해당하는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueResVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(11, "프로젝트가 존재하지 않습니다.");
            }
            /** 현재 변경하려는 이슈에 해당하는 사용자 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueResVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(12, "사용자가 존재하지 않습니다.");
            }
            /** 현재 변경하려는 기존 이슈 조회 */
            IssueEntity issueEntity = issueRepository.findByTitle(issueResVo.getTitle());
            if(issueEntity == null) {
                return new ResponseVo(13,"이슈가 존재하지 않습니다.");
            }
            /** reporter 변경 시 존재하는 사용자인지 체크 */
            MemberEntity chkReporter = memberRepository.findByUserId(issueResVo.getReporter());
            if(chkReporter == null) {
                return new ResponseVo(14, "레포터 사용자가 존재하지 않습니다.");
            }

            /** 이슈의 상태 및 reporter 변경 > test1 사용자 */
            IssueEntity updateIssue = IssueEntity.builder()
                    .issueId(issueEntity.getIssueId())
                    .title(issueResVo.getTitle())
                    .description(issueResVo.getDescription())
                    .reporter(issueResVo.getReporter())
                    .date(issueResVo.getDate())
                    .fixer(issueResVo.getFixer())
                    .assignee(issueResVo.getAssignee())
                    .priority(issueResVo.getPriority())
                    .state(issueResVo.getState())
                    .projectId(projectEntity.getProjectId())
                    .memberId(memberEntity.getMemberId())
                    .build();
            issueRepository.save(updateIssue);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(9,"FAIL");
        }
    }

    public ResponseVo<IssueResVo> selectAllIssues() {
        try {
            /** 모든 이슈 조회 - PL1 */
            List<IssueEntity> issueEntities = issueRepository.findAll();
            List<IssueResVo> resultList = new ArrayList<>();
            issueEntities.stream().map(e -> {
                IssueResVo issueResVo = IssueResVo.builder()
                        .issueId(e.getIssueId())
                        .title(e.getTitle())
                        .description(e.getDescription())
                        .reporter(e.getReporter())
                        .date(e.getDate())
                        .fixer(e.getFixer())
                        .assignee(e.getAssignee())
                        .priority(e.getPriority())
                        .state(e.getState())
                        .build();
                resultList.add(issueResVo);
                return issueResVo;
            }).collect(Collectors.toList());
            return new ResponseVo<IssueResVo>(200,"SUCCESS",resultList);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }

    public ResponseVo<IssueResVo> selectStateNew(String state) {
        if(StringUtils.isEmpty(state)) {
            return null;
        }
        try {
            /** 상태값에 따라 이슈 조회하기(현재 당사자에 한해) */
            List<IssueEntity> issueEntities = issueRepository.findByState(state);
            List<IssueResVo> resultList = new ArrayList<>();
            if(issueEntities.size() > 0) {
                issueEntities.stream().map(e -> {
                    IssueResVo issueResVo = IssueResVo.builder()
                            .issueId(e.getIssueId())
                            .title(e.getTitle())
                            .description(e.getDescription())
                            .reporter(e.getReporter())
                            .date(e.getDate())
                            .fixer(e.getFixer())
                            .assignee(e.getAssignee())
                            .priority(e.getPriority())
                            .state(e.getState())
                            .build();
                    resultList.add(issueResVo);
                    return issueResVo;
                }).collect(Collectors.toList());
            }
            return new ResponseVo<>(200,"SUCCESS", resultList);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }

    public ResponseVo updateaAssignee(IssueResVo issueResVo) {
        if(ObjectUtils.isEmpty(issueResVo)) {
            return null;
        }
        try {
            /** 변경하려는 이슈에 해당하는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueResVo.getProjectNm());
            if(projectEntity == null) {
                return new ResponseVo(11,"프로젝트가 존재하지 않습니다.");
            }
            /** 변경하려는 이슈에 해당하는 사용자 조회 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueResVo.getUserId());
            if(memberEntity == null) {
                return new ResponseVo(12,"사용자가 존재하지 않습니다.");
            }
            /** 변경하려는 이슈 조회 */
            IssueEntity issueEntity = issueRepository.findByTitle(issueResVo.getTitle());
            if(issueEntity == null) {
                return new ResponseVo(13,"이슈가 존재하지 않습니다.");
            }
            /** 변경하려는 assignee 사용자가 존재하는지 확인 */
            MemberEntity chkMember = memberRepository.findByUserId(issueResVo.getAssignee());
            if(chkMember == null) {
                return new ResponseVo(14,"할당자가 존재하지 않습니다.");
            }
            /** 이슈 assignee 변경 */
            IssueEntity updateIssue = IssueEntity.builder()
                    .issueId(issueEntity.getIssueId())
                    .title(issueResVo.getTitle())
                    .description(issueResVo.getDescription())
                    .reporter(issueResVo.getReporter())
                    .date(issueResVo.getDate())
                    .fixer(issueResVo.getFixer())
                    .assignee(issueResVo.getAssignee())
                    .priority(issueResVo.getPriority())
                    .state(issueResVo.getState())
                    .projectId(projectEntity.getProjectId())
                    .memberId(memberEntity.getMemberId())
                    .build();
            issueRepository.save(updateIssue);
            return new ResponseVo(200,"SUCCESS");
        } catch (Exception e) {
            return new ResponseVo(99,"FAIL");
        }
    }




    /***
     *** MyBatis 사용
     ***/

    /**
     * PL1 > 모든 이슈 조회하되 상태값 따라 조회도 가능
     * */
    public ResponseVo<IssueResVo> selectAllByCondition(IssueReqVo issueResVo) {
        if(ObjectUtils.isEmpty(issueResVo)) {
            return null;
        }
        try {
            List<IssueResVo> list = issueMapper.selectAllByCondition(issueResVo);
            return new ResponseVo<>(200,"SUCCESS",list);
        } catch (Exception e) {
            return new ResponseVo<>(99,"FAIL");
        }
    }

}
