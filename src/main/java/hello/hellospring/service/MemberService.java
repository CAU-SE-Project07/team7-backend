package hello.hellospring.service;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;

public interface MemberService {
    ResponseVo insertMember(MemberVo memberVo);
    ResponseVo updateMember(MemberVo memberVo);
    ResponseVo login(MemberVo memberVo);
    ResponseVo<MemberVo> selectAllUsers();
    //정보 다긁어오기
    //지우기
    //역할기반 긁어오기
}