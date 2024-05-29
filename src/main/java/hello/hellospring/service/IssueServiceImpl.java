package hello.hellospring.service;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.IssueCreateVo;
import hello.hellospring.Vo.IssueUpdateVo;
import hello.hellospring.Vo.IssueVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.recommendation.PythonScriptExecutor;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.IssueRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
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
    public ResponseVo<IssueVo> insertIssue(IssueCreateVo issueVo) {
        try {
            if (ObjectUtils.isEmpty(issueVo)) {
                return null;
            }
            /** 등록하려는 이슈 제목 중복 체크 */
            IssueEntity isExistedIssueEntity = issueRepository.findByTitle(issueVo.getTitle());
            if (isExistedIssueEntity != null) {
                return new ResponseVo<IssueVo>(11, "Title is duplicated.");
            }
            /** 이슈 기본키 : issueId => 고유값 처리 */
            int issueId = 1;
            int count = issueRepository.findAll().size();
            if (count > 0) {
                issueId = count + 1;
            }
            /** 우선순위 처리 */
            if (issueVo.getPriority() == null || issueVo.getPriority().equals("")) {
                issueVo.setPriority("MAJOR");
            }
            /** 등록하려는 프로젝트 조회 */
            ProjectEntity projectEntity = projectRepository.findByProjectNm(issueVo.getProjectNm());
            if (projectEntity == null) {
                return new ResponseVo<IssueVo>(99, "FAILED, Project not found. [InsertIssue]");
            }
            /** 사용자 찾기 */
            MemberEntity memberEntity = memberRepository.findByUserId(issueVo.getUserId());
            if (memberEntity == null) {
                return new ResponseVo<IssueVo>(99, "FAILED, Member not found. [InsertIssue]");
            }
            IssueEntity issueEntity = IssueEntity.builder()
                    .issueId(issueId)
                    .title(issueVo.getTitle())
                    .description(issueVo.getDescription())
                    .reporter(memberEntity.getUserNm())//새로 만든 사람이 무조건 reporter
                    .date(issueVo.getDate())
                    .fixer(null)
                    .assignee(null)
                    .priority(issueVo.getPriority())
                    .state("NEW") //새로 만들어진 이슈는 무조건 NEW
                    .memberId(memberEntity)
                    .projectId(projectEntity)
                    .build();
            issueRepository.save(issueEntity);

            List<IssueVo> resultList = new ArrayList<>();
            IssueVo issueVo1 = convertToVo(issueEntity);
            resultList.add(issueVo1);

            return new ResponseVo<IssueVo>(200, "SUCCESS", resultList);
        } catch (Exception e) {
            return new ResponseVo<IssueVo>(99, "FAIL");
        }
    }

    @Override
    public ResponseVo<IssueVo> updateIssue(IssueUpdateVo issueVo) {
        try {
            /** 기존 이슈 긁어오기 */
            IssueEntity origEntity = issueRepository.findByTitle(issueVo.getTitle());
            if(origEntity==null)
            {
                return new ResponseVo<IssueVo>(99, "FAILED, Issue not found. [UpdateIssue]");
            }
            if(!issueVo.getProjectNm().equals(origEntity.getProjectId().getProjectNm()))
            {
                return new ResponseVo<IssueVo>(99, "FAILED, Issue not found in that project. [UpdateIssue]");
            }
            /**
             * 이슈에 대해 dev1을 담당자(assignee)로 지정하며,
             * 코멘트에 적절한 메시지를 추가함.
             * 이후 시스템은 해당 이슈의 상태를 assigned로 변경함
             * 즉, assignee가 바뀌면 state를 assigned로 바꿈**/
            String changedState = issueVo.getState();
            String changedAssignee = origEntity.getAssignee();
            if (!issueVo.getAssignee().isEmpty() && !issueVo.getAssignee().equals(origEntity.getAssignee())) {
                if (memberRepository.findByUserNm(issueVo.getAssignee()) == null) {
                    return new ResponseVo<IssueVo>(99, "FAILED, Member " +issueVo.getAssignee() +" not found. [UpdateIssue]");
                }
                changedState = "ASSIGNED";
                changedAssignee = issueVo.getAssignee();
            }
            /** 전에 Assigned였고 이번에 fixed로 바뀌었으면 assignee를 fixer로 지정
             * 이외엔 원래의 fixer로 지정**/
            String chagnedFixer = origEntity.getFixer();
            if (origEntity.getState().equals("ASSIGNED") && issueVo.getState().equals("FIXED")) {
                chagnedFixer = origEntity.getAssignee();
            }
            /**
             * 값이 들어오면 바뀌고 아니면 그대로 DESCRIPTION
             * */
            String des=origEntity.getDescription();
            if(!issueVo.getDescription().isEmpty())
            {
                des = issueVo.getDescription();
            }
            /**
             * 값이 들어오면 바뀌고 아니면 그대로 TITLE
             * */
            String tit = origEntity.getTitle();
            if(!issueVo.getTitle().isEmpty())
            {
                tit = issueVo.getTitle();
            }
            /**
             * 값이 들어오면 바뀌고 아니면 그대로 PRIORITY
             * */
            String priority = origEntity.getPriority();
            if(!issueVo.getPriority().isEmpty())
            {
                priority = issueVo.getPriority();
            }



            IssueEntity updateIssue = IssueEntity.builder().
                    issueId(origEntity.getIssueId()).
                    title(tit).
                    description(des).
                    reporter(origEntity.getReporter()). //reporter는 안바뀜
                            date(origEntity.getDate()).
                    fixer(chagnedFixer).
                    assignee(changedAssignee).
                    priority(priority).
                    state(changedState).
                    memberId(origEntity.getMemberId()).
                    projectId(origEntity.getProjectId()).
                    commentEntities(origEntity.getCommentEntities()).
                    build();
            //int addingCommentId = origEntity.getCommentEntities().getFirst().getCommentId();
            //addingCommentId++;
            List<IssueVo> resultList = new ArrayList<>();
            IssueVo issueVo1 = convertToVo(updateIssue);
            resultList.add(issueVo1);

            issueRepository.save(updateIssue);
            return new ResponseVo<IssueVo>(200, "SUCCESS",resultList);
        } catch (Exception e) {
            return new ResponseVo<IssueVo>(99, "FAIL"+e.toString());
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
                    .build();
            resultList.add(issueVo);
            return issueVo;
        }).collect(Collectors.toList());
        return new ResponseVo<IssueVo>(200, "SUCCESS", resultList);
    }

    @Override
    public IssueVo convertToVo(IssueEntity issueEntity) {
        return IssueVo.builder()
                .issueId(issueEntity.getIssueId())
                .title(issueEntity.getTitle())
                .description(issueEntity.getDescription())
                .reporter(issueEntity.getReporter())
                .date(issueEntity.getDate())
                .fixer(issueEntity.getFixer())
                .assignee(issueEntity.getAssignee())
                .priority(issueEntity.getPriority())
                .state(issueEntity.getState())
                .build();
    }

        @Override
        public List<IssueVo> getIssuesByAssignee(String assignee) {
            List<IssueEntity> issueEntities = issueRepository.findIssuesByAssignee(assignee);
            return issueEntities.stream()
                    .map(this::convertToVo)
                    .collect(Collectors.toList());
        }
        @Override
    public String recommendAssignee(String issueTitle) {
        IssueEntity currentIssue = issueRepository.findByTitle(issueTitle);
        if (currentIssue == null) {
            return "Issue not found";
        }

        String issueDescription = currentIssue.getDescription();
        List<IssueEntity> allIssues = issueRepository.findAll();

        Map<String, Double> assigneeRelevanceScore = new HashMap<>();

        for (IssueEntity issue : allIssues) {
            if (issue.isResolved()||issue.isClosed()) {
                double similarity = 0.0;
                System.out.println("oh my god!");
                try {
                    similarity = PythonScriptExecutor.calculateSimilarity(issueDescription, issue.getDescription());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Issue ID: " + issue.getIssueId() + " | Similarity: " + similarity + " | Assignee: " + issue.getAssignee());
                if (similarity!=0) {
                    String assignee = issue.getAssignee();
                    assigneeRelevanceScore.put(assignee, assigneeRelevanceScore.getOrDefault(assignee, 0.0) + similarity);
                }
            }
        }
    System.out.println(assigneeRelevanceScore.size());


            Optional<Map.Entry<String, Double>> bestAssignee = assigneeRelevanceScore.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            return bestAssignee.map(Map.Entry::getKey).orElse("No suitable assignee found");
    }


}
