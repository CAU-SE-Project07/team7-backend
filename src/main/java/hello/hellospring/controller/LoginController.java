package hello.hellospring.controller;

import hello.hellospring.dto.LoginRequestDTO;
import hello.hellospring.dto.LoginResponseDTO;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginMember(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean isAuthenticated = memberService.authenticateMember(loginRequestDTO.getUserId(), loginRequestDTO.getPassword());
        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setSuccess(isAuthenticated);
        if (isAuthenticated) {
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(401).body(responseDTO); // Unauthorized
        }
    }
}