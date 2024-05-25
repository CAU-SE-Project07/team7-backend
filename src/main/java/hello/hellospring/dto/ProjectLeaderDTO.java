package hello.hellospring.dto;

import lombok.Data;

@Data
public class ProjectLeaderDTO {
    private int id;
    private String userId;
    private String password;
    private String email;

    // Getters and Setters
}
