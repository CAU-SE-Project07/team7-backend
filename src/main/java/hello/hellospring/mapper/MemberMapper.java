package hello.hellospring.mapper;

import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.MemberResVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    void addMember(MemberReqVo memberReqVo);
    List<MemberResVo> selectMembers(MemberReqVo memberReqVo);
    void updateMember(MemberReqVo memberReqVo);
}
