package hello.hellospring.service;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;

import java.util.List;

public interface MemberService {
    ResponseVo insertMember(MemberVo memberVo);
    ResponseVo updateMember(MemberVo memberVo);
    ResponseVo login(MemberVo memberVo);
    ResponseVo<MemberVo> findBymemberId(String memberId);
    //정보 다긁어오기
    //지우기
    ResponseVo<MemberVo> selectAllUsers();
    ResponseVo deleteUsers(List<MemberVo> memberVoList);
    //역할기반 긁어오기
}