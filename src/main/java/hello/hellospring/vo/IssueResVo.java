package hello.hellospring.vo;

import lombok.Builder;
import lombok.Data;

/**
 * Java 객체 클래스
 * */
@Data
@Builder
public class IssueResVo {
    private int issueId;
    private String title;
    private String description;
    private String reporter;
    private String date;
    private String fixer;
    private String assignee;
    private String priority;
    private String state;
    private String memberId;
    private String projectId;
}
