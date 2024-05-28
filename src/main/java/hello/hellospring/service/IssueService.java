package hello.hellospring.service;

import hello.hellospring.Entity.CommentEntity;
import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Enum.Priority;
import hello.hellospring.Enum.State;
import hello.hellospring.Vo.IssueVo;
import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;

import java.util.Date;
import java.util.List;

public interface IssueService {
    ResponseVo insertIssue(IssueVo issueVo);
    ResponseVo updateIssue(IssueVo issueVo);
    ResponseVo<IssueVo> getListByUserIdAndState(String userId, String state);
}