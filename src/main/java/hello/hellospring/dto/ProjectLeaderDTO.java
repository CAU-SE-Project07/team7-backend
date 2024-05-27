package hello.hellospring.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectLeaderDTO {
    private int id;
    private String userId;
    private String password;
    private String email;
    private List<Integer> projectIds;

    // Getters and Setters
}
