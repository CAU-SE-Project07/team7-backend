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
    private int commentId; // 아이디
    @Column
    private String content; // 댓글 내용
    @Column
    private Date date; // 등록 날짜
    @Column
    private int projectId; // 프로젝트 아이디(외래키)
    @Column
    private int memberId; // 사용자 아이디(외래키)
    @Column
    private int issueId; // 이슈 아이디(외래키)

    public CommentEntity () {};
}
