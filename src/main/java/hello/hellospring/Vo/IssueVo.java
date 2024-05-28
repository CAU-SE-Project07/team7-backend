package hello.hellospring.Vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Java 객체 클래스
 * */
@Data
@Builder
public class IssueVo {
    private int issueId;
    private String title;
    private String description;
    private String reporter;
    private String date;
    private String fixer;
    private String assignee;
    private String priority;
    private String state;
    private String userId;
    private String projectNm; // 프로젝트명(고유값)
    private CommentVo comment;
}
