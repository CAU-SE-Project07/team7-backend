package hello.hellospring.controller;

import hello.hellospring.Entity.CommentEntity;
import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Enum.Priority;
import hello.hellospring.Enum.State;
import hello.hellospring.Vo.*;
import hello.hellospring.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    /**
     * 이슈 추가 - Test 권한자
     * */
    @PostMapping("/addIssue")
    public ResponseVo<IssueVo> createTicket(@RequestBody IssueCreateVo issueCreateVo) {
        return issueService.insertIssue(issueCreateVo);
    }

    /**
     * 이슈 조회 - 할당자가 상태로 검색 - WHERE : 할당자 & 상태
     * */
    @GetMapping("/title/{userId}/{state}")
    public ResponseVo<IssueVo> findIssueListByUserIdAndState(@PathVariable String userId, @PathVariable String state) {
        return issueService.getListByUserIdAndState(userId, state);
    }
    /**
     * 이슈 수정
     */
    @PutMapping("/updateIssue")
    public ResponseVo<IssueVo> updateTicket(@RequestBody IssueUpdateVo issueUpdateVo)
    {
        return issueService.updateIssue(issueUpdateVo);
    }
    /**
     * 특정 assignee 에게 할당된 Issue들만 찾아보기
     * */
    @GetMapping("/assignee/{assignee}")
    public List<IssueVo> getIssuesByAssignee(@PathVariable String assignee) {
        return issueService.getIssuesByAssignee(assignee);
    }
    /**
     * 실험단계
     * 유사한 문제 해결 많이 했던 Assignee 추천
     * */
    @GetMapping("/recommend-assignee/{issueTitle}/{projectNm}")
    public String recommendAssignee(@PathVariable String issueTitle, @PathVariable String projectNm) {
        return issueService.recommendAssignee(issueTitle,projectNm);
    }
    /**해당 프로젝트에*/
    @GetMapping("/allIssues")
    public List<IssueVo> getIssuesByProjectName() {
        logger.info("Received request to get all issues");
        List<IssueVo> issues = issueService.getIssuesByProjectName();
        logger.info("Returning {} issues", issues.size());
        return issues;
    }

    /**
     * reporter기반 다긁어오기
     * */
    @GetMapping("/reporterName/{reporterName}")
    public List<IssueVo> getIssuesByReporterName(@PathVariable String reporterName) {
        return issueService.getIssuesByReporter(reporterName);
    }

    /**
     * title기반 긁어오기
     */
    @GetMapping("/issueTitle/{issueTitle}")
    public ResponseVo<IssueVo> getIssuesByIssueTitle(@PathVariable String issueTitle) {
        return issueService.getIssueByTitle(issueTitle);
    }
}

