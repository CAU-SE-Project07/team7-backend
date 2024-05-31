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

    /**
     * MyBatis 사용
     * */

    /**
     * MyBatis - 사용자 추가
     * */
    @PostMapping("/01")
    public ResponseVo addMember(@RequestBody MemberReqVo memberReqVo) {
        return memberService.addMember(memberReqVo);
    }

    /**
     * 사용자 조회
     * */
    @PostMapping("/02")
    public ResponseVo<MemberResVo> selectMember(@RequestBody MemberReqVo memberReqVo) {
        return memberService.selectMember(memberReqVo);
    }

    /**
     * MyBatis - 사용자 변경
     * */
    @PostMapping("/03")
    public ResponseVo updateMember(@RequestBody MemberReqVo memberReqVo) {
        return memberService.updateMember(memberReqVo);
    }
}
