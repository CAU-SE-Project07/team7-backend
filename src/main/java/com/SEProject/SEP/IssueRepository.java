package com.SEProject.SEP;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {

    Issue findBySubject(String subject);
    Issue findBySubjectAndContent(String subject, String content);
    List<Issue> findBySubjectLike(String subject);
}
