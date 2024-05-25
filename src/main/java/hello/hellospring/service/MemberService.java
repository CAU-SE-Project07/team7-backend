package hello.hellospring.service;

import hello.hellospring.vo.MemberVo;
import hello.hellospring.vo.ResponseVo;

public interface MemberService {
    ResponseVo insertMember(MemberVo memberVo);
    ResponseVo updateMember(MemberVo memberVo);
}