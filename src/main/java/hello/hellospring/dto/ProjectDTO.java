package hello.hellospring.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectDTO {
    private String projectName;
    private String projectDesc;
    private List<Integer> developerIds;
    private List<Integer> testerIds;
    private List<Integer> projectLeaderIds;
}
