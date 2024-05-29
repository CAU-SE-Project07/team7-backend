package hello.hellospring.Vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueUpdateVo {
    private String title;
    private String description;
    private String date;
    private String assignee;
    private String priority;
    private String state;
    private String projectNm; // 프로젝트명(고유값)
}
