package hello.hellospring.mapper;

import hello.hellospring.vo.CommentReqVo;
import hello.hellospring.vo.IssueReqVo;
import hello.hellospring.vo.IssueResVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentReqVo commentReqVo);
}
