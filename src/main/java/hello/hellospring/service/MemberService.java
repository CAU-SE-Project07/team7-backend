package hello.hellospring.service;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;

import java.util.List;

public interface MemberService {
    ResponseVo insertMember(MemberVo memberVo);
    ResponseVo updateMember(MemberVo memberVo);
    ResponseVo login(MemberVo memberVo);
    ResponseVo<MemberVo> selectAllUsers();
    ResponseVo deleteUsers(List<MemberVo> memberVoList);
    //역할기반 긁어오기
}