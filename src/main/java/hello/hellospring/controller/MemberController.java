package hello.hellospring.controller;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/addMember")
    public ResponseVo insertMember(@RequestBody MemberVo memberVo) {
        return memberService.insertMember(memberVo);
    }

    @PostMapping("/updateMember")
    public ResponseVo updateMember(@RequestBody MemberVo memberVo) {
        return memberService.updateMember(memberVo);
    }

    /***
     * 민영 - 로그인
     */
    @PostMapping("/login")
    public ResponseVo login(@RequestBody MemberVo memberVo) {
        return memberService.login(memberVo);
    }

    /**
     * 모든 사용자 조회
     * */
    @GetMapping("/allUsers")
    public ResponseVo<MemberVo> selectAllUsers() {
        return memberService.selectAllUsers();
    }

}
