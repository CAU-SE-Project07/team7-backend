package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import hello.hellospring.vo.MemberReqVo;
import hello.hellospring.vo.MemberResVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/addMember")
    public ResponseVo insertMember(@RequestBody MemberReqVo memberReqVo) {
        return memberService.insertMember(memberReqVo);
    }

    @PostMapping("/updateMember")
    public ResponseVo updateMember(@RequestBody MemberReqVo memberReqVo) {
        return memberService.updateMember(memberReqVo);
    }

    /**
     * MyBatis 사용
     * */

    /**
     * 사용자 조회
     * */
    @PostMapping("/01")
    public ResponseVo<MemberResVo> selectUser(@RequestBody MemberReqVo memberReqVo) {
        return memberService.selectUser(memberReqVo);
    }
}
