package hello.hellospring.Vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectVo {
    private String projectNm;
    private String projectDesc;
}
