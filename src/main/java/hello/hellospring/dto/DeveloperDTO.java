package hello.hellospring.dto;

import lombok.Data;

import java.util.List;

@Data
public class DeveloperDTO {
    private int id;
    private String userId;
    private String password;
    private String email;
    private List<Integer> assignedTickets;
    private List<Integer> fixedTickets;
    private List<Integer> projectIds;

    // Getters and Setters
}