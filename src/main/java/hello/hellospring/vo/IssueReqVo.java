package hello.hellospring.vo;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

/**
 * Java 객체 클래스
 * */
@Data
@Builder
public class IssueReqVo {
    private int issueId;
    private String title;
    private String description;
    private String reporter;
    private Date date;
    private String fixer;
    private String assignee;
    private String priority;
    private String state;
    private String memberId;
    private String projectId;
    private String userRoles;
}
