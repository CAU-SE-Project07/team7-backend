package com.SEProject.SEP.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name ="users")
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //전부 들어있는 생성자
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "user_name")
    private String userName;

    @Column
    private String password;

    @Column(unique = true, nullable = true)
    private String email;

    @Column(nullable = true)
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Issue> issueList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comment;




}
