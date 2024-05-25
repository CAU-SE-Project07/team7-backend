package com.SEProject.SEP.user.controller;

import com.SEProject.SEP.user.dto.LoginRequest;
import com.SEProject.SEP.user.dto.UserDto;
import com.SEProject.SEP.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> test(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);

        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto){
        try {
            String userid = userService.login(userDto.getUserName(), userDto.getPassword());
            return ResponseEntity.ok("로그인 성공. 사용자 아이디 : " + userid);
        } catch (RuntimeException e){
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }
}
