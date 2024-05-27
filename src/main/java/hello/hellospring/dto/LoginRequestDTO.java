package hello.hellospring.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userId;
    private String password;
}