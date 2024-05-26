package hello.hellospring.controller;

import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.MemberVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 이슈 조회 - PL1이 모든 이슈를 브라우즈 > 본인에게 해당하는 이슈들만 조회
     * */
    @PostMapping("/allIssues")
    public ResponseVo<IssueVo> selectAllIssuesByPL(@RequestBody MemberVo memberVo) {
        return issueService.selectAllIssuesByPL(memberVo);
    }

    /**
     * 이슈 조회 - 할당자가 상태로 검색 - WHERE : 할당자 & 상태
     * */
//    @GetMapping("/title/{userId}/{state}")
//    public ResponseVo<IssueVo> findIssueListByUserIdAndState(@PathVariable String userId, @PathVariable String state) {
//        return issueService.getListByUserIdAndState(userId, state);
//    }

}

