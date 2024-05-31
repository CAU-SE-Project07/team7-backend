package hello.hellospring.mapper;

import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.ResponseVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssueMapper {
    List<IssueResVo> selectIssues(IssueReqVo issueReqVo);
    void updateIssue(IssueReqVo issueReqVo);
    void addIssue(IssueReqVo issueReqVo);
    IssueResVo selectById(IssueReqVo issueReqVo);
}
