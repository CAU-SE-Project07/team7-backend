package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "ISSUE")
@Data
@Builder
@AllArgsConstructor
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private ProjectEntity projectId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberId;
    @OneToMany(mappedBy = "issueId", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntities ;

    public IssueEntity() {}
    public boolean isResolved(){return state.equals("RESOLVED");}
    public boolean isClosed(){return state.equals("CLOSED");}
}
