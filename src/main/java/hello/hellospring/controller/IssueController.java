package hello.hellospring.controller;

import hello.hellospring.service.IssueService;
import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    /***
     *** MyBatis 사용
     * */

    /**
     * test1 > 이슈 생성
     * */
    @PostMapping("/01")
    public ResponseVo addIssue(@RequestBody IssueReqVo issueReqVo) {
        return issueService.addIssue(issueReqVo);
    }

    /**
     * test1/PL1 > 이슈 변경
     * */
    @PostMapping("/02")
    public ResponseVo updateIssue(@RequestBody IssueReqVo issueReqVo) {
        return issueService.updateIssue(issueReqVo);
    }

    /**
     * 이슈 조회(조건 별 검색 가능)
     * @param : issueId, title, reporter, fixer, assignee, state
     * */
    @PostMapping("/03")
    public ResponseVo<IssueResVo> selectIssueList(@RequestBody IssueReqVo issueReqVo) {
        return issueService.selectIssueList(issueReqVo);
    }

}

