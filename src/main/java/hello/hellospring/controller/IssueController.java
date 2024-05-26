package hello.hellospring.controller;

import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

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
     * 이슈 조회 - 할당자가 상태로 검색 - WHERE : 할당자 & 상태
     * */
    @GetMapping("/title/{userId}/{state}")
    public ResponseVo<IssueVo> findIssueListByUserIdAndState(@PathVariable String userId, @PathVariable String state) {
        return issueService.getListByUserIdAndState(userId, state);
    }

}
