package hello.hellospring.controller;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.IssueService;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/updateMember")
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

    /**
     * 다중 사용자 삭제
     * */
    @DeleteMapping("/deleteUsers")
    public ResponseVo deleteUsers(@RequestBody List<MemberVo> memberVoList) {
        return memberService.deleteUsers(memberVoList);
    }

    @GetMapping("/memberInfo/{userId}")
    public ResponseVo<MemberVo> getMemberInfo(@PathVariable String userId) {
        return memberService.findBymemberId(userId);
    }

}
