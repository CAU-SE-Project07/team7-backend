package com.SEProject.SEP.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
