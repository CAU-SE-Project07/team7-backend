package hello.hellospring.mapper;

import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import hello.hellospring.vo.ResponseVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssueMapper {
    List<IssueResVo> selectAllByCondition(IssueReqVo issueReqVo);
    ResponseVo updateAssignee(IssueReqVo issueReqVo);
}
