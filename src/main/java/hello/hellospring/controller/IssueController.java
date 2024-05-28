package hello.hellospring.controller;

import hello.hellospring.Entity.CommentEntity;
import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Enum.Priority;
import hello.hellospring.Enum.State;
import hello.hellospring.Vo.IssueVo;
import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.IssueService;
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

    /**
     * 이슈 추가 - Test 권한자
     * */
    @PostMapping("/addIssue")
    public ResponseVo createTicket(@RequestBody IssueVo issueVo) {
        return issueService.insertIssue(issueVo);
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
    public ResponseVo updateTicket(@RequestBody IssueVo issueVo)
    {
        return issueService.updateIssue(issueVo);
    }
    /**
     * 특정 assignee 에게 할당된 Issue들만 찾아보기
     * */
    @GetMapping("/assignee/{assignee}")
    public List<IssueEntity> getIssuesByAssignee(@PathVariable String assignee) {
        return issueService.getIssuesByAssignee(assignee);
    }

}

