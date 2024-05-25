package com.SEProject.SEP.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //전부 들어있는 생성자
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String writer;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
