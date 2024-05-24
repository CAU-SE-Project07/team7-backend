package hello.hellospring.service;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;

public interface MemberService {
    ResponseVo insertMember(MemberVo memberVo);
    ResponseVo updateMember(MemberVo memberVo);
}