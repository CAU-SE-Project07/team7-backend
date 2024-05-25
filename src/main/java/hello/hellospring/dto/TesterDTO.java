package hello.hellospring.dto;

import lombok.Data;

import java.util.List;

@Data
public class TesterDTO {
    private int id;
    private String userId;
    private String password;
    private String email;
    private List<Integer> reportedTickets;

    // Getters and Setters
}
