package hello.hellospring.controller;

import hello.hellospring.service.IssueService;
import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    /**
     * 이슈 추가 - Test 권한자 => 제목/내용/날짜만 추가
     * */
    @PostMapping("/addIssue")
    public ResponseVo createIssue(@RequestBody IssueVo issueVo) {
        return issueService.insertIssue(issueVo);
    }

    /**
     * 이슈 변경 - 상태 + reporter
     * */
    @PostMapping("/updateStsRpt")
    public ResponseVo updateIssueOfStateAndReporter(@RequestBody IssueVo issueVo) {
        return issueService.updateIssue(issueVo);
    }

    /**
     * 이슈 조회 - PL1이 모든 이슈 조회
     * */
    @PostMapping("/allIssues")
    public ResponseVo<IssueVo> selectAllIssues() {
        return issueService.selectAllIssues();
    }

    /**
     * 이슈 조회 - PL1이 모든 이슈 중, 상태 값이 NEW인 것만 조회(받은 상태값에 따라 조회)
     * */
    @PostMapping("/selectStateNew")
    public ResponseVo<IssueVo> selectStateNew(@RequestBody String state) {
        return issueService.selectStateNew(state);
    }

    /**
     * 이슈 변경 - PL1 > assignee 변경(admin으로)
     * */
    @PostMapping("/updateaAssignee")
    public ResponseVo updateaAssignee(@RequestBody IssueVo issueVo) {
        return issueService.updateaAssignee(issueVo);
    }

}

