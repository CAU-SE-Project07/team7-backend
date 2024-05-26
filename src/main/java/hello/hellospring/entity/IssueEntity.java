package hello.hellospring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity(name = "ISSUE")
@Data
@Builder
@AllArgsConstructor
public class IssueEntity {
    @Id
    private int issueId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String reporter; //tester, 보고자
    @Column
    private String date; // 등록 날짜
    @Column
    private String fixer; //dev, 최종 확인자(최종 권한)
    @Column
    private String assignee; //PL1, 중간 확인자(중간 권한)
    @Column
    private String priority; //우선 순위
    @Column
    private String state; // 상태 값
    @Column
    private int projectId; // 프로젝트 아이디(외래키)
    @Column
    private int memberId; // 사용자 아이디(외래키)

    public IssueEntity() {}
}
