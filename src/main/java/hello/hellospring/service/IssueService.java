package hello.hellospring.service;

import hello.hellospring.vo.IssueVo;
import hello.hellospring.vo.MemberVo;
import hello.hellospring.vo.ResponseVo;

public interface IssueService {
    ResponseVo insertIssue(IssueVo issueVo);
    ResponseVo updateIssue(IssueVo issueVo);
    ResponseVo<IssueVo> selectAllIssues();
    ResponseVo<IssueVo> selectStateNew(String state);
    //ResponseVo<IssueVo> getListByUserIdAndState(String userId, String state);
}