package hello.hellospring.controller;

import hello.hellospring.vo.MemberVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseVo insertMember(@RequestBody MemberVo memberVo) {
        return memberService.insertMember(memberVo);
    }

    @PostMapping("/updateMember")
    public ResponseVo updateMember(@RequestBody MemberVo memberVo) {
        return memberService.updateMember(memberVo);
    }
}
