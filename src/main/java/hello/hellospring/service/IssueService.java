package hello.hellospring.service;

import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.ResponseVo;

public interface IssueService {
    ResponseVo insertIssue(IssueVo issueVo);
    ResponseVo updateIssue(IssueVo issueVo);
    ResponseVo<IssueVo> getListByUserIdAndState(String userId, String state);
}