package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity(name = "COMMENT")
@Data
@Builder
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId; // 아이디
    @Column
    private String content; // 댓글 내용
    @Column
    private String date; // 등록 날짜
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private ProjectEntity projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issueId")
    private IssueEntity issueId;

    public CommentEntity () {};
}
