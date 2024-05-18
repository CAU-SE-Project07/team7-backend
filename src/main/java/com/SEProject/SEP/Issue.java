package com.SEProject.SEP;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition ="TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)//이슈 삭제시 코멘트 삭제
    private List<Comment> commentList;
}
