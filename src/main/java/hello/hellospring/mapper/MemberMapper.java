package hello.hellospring.mapper;

import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.MemberResVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberResVo selectUser(MemberReqVo memberReqVo);
}
