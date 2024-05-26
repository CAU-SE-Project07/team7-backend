package hello.hellospring.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectVo {
    private int projectId;
    private String projectNm;
    private String projectDesc;
}
