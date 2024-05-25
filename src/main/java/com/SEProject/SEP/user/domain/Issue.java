package com.SEProject.SEP.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //전부 들어있는 생성자
public class Issue {

    //제목, 내용, 작성자, 우선순위(중요, 덜중요,짱중요), 상태(new-> assign-> fixed -> resolved-> closed)

    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition ="TEXT")
    private String content;

    private String reporter;

    private String priority;

    private String state;

    private String assignee;

    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)//이슈 삭제시 코멘트 삭제
    private List<Comment> commentList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Issue(String title, String content, String reporter, String priority, String state, String assignee) {
        this.title = title;
        this.content = content;
        this.reporter = reporter;
        this.priority = priority;
        this.state = state;
        this.assignee = assignee;
    }

    public void update(Issue issue) {
        this.title = issue.getTitle();
        this.content = issue.getContent();
        this.reporter = issue.getReporter();
        this.priority = issue.getPriority();
        this.state = issue.getState();
        this.assignee = issue.getAssignee();
    }

}
