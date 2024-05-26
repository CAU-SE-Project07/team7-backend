package hello.hellospring.controller;

import hello.hellospring.service.IssueService;
import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    /**
     * JPA 사용
     * */

    /**
     * 이슈 추가 - Test 권한자 => 제목/내용/날짜만 추가
     * */
    @PostMapping("/addIssue")
    public ResponseVo createIssue(@RequestBody IssueReqVo issueReqVo) {
        return issueService.insertIssue(issueReqVo);
    }

    /**
     * 이슈 변경 - 상태 + reporter
     * */
    @PostMapping("/updateStsRpt")
    public ResponseVo updateIssueOfStateAndReporter(@RequestBody IssueReqVo issueReqVo) {
        return issueService.updateIssue(issueReqVo);
    }

    /**
     * 이슈 조회 - PL1이 모든 이슈 조회
     * */
    @PostMapping("/allIssues")
    public ResponseVo<IssueResVo> selectAllIssues() {
        return issueService.selectAllIssues();
    }

    /**
     * 이슈 조회 - PL1이 모든 이슈 중, 상태 값이 NEW인 것만 조회(받은 상태값에 따라 조회)
     * */
    @PostMapping("/selectStateNew")
    public ResponseVo<IssueResVo> selectStateNew(@RequestBody String state) {
        return issueService.selectStateNew(state);
    }

    /**
     * 이슈 변경 - PL1 > assignee 변경(admin으로)
     * */
    @PostMapping("/updateaAssignee")
    public ResponseVo updateaAssignee(@RequestBody IssueReqVo issueReqVo) {
        return issueService.updateaAssignee(issueReqVo);
    }



    /***
     *** MyBatis 사용
     * */

    /**
     * PL1 > 모든 이슈를 조회할 수 있되 상태값에 따라 조회도 가능
     * */
    @PostMapping("/01")
    public ResponseVo<IssueResVo> selectAllByCondition(@RequestBody IssueReqVo issueReqVo) {
        return issueService.selectAllByCondition(issueReqVo);
    }

    /**
     * PL1 > Assignee 변경(admin으로)
     * */
    @PostMapping("/02")
    public void updateAssignee(@RequestBody IssueReqVo issueReqVo ) throws Exception {
        issueService.updateAssignee(issueReqVo);
    }

}

