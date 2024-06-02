package hello.hellospring.Vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueCreateVo {
    private int issueId;
    private String title;
    private String description;
    private String date;
    private String priority;
    private String userId;
}
