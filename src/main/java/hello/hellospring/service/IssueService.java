package hello.hellospring.service;

import hello.hellospring.Entity.CommentEntity;
import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Enum.Priority;
import hello.hellospring.Enum.State;
import hello.hellospring.Vo.*;

import java.util.Date;
import java.util.List;

public interface IssueService {
    ResponseVo<IssueVo> insertIssue(IssueCreateVo issueVo);
    ResponseVo<IssueVo> updateIssue(IssueUpdateVo issueVo);
    ResponseVo<IssueVo> getListByUserIdAndState(String userId, String state);
    IssueVo convertToVo(IssueEntity issueEntity);

    public List<IssueVo> getIssuesByAssignee(String assignee);
    public String recommendAssignee(String issueId,String ProjectNm);
    public List<IssueVo> getIssuesByProjectName(String projectNm);
}

