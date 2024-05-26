package com.SEProject.SEP.user.repository;

import com.SEProject.SEP.user.domain.Issue;
import com.SEProject.SEP.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue,Long> {

    Optional<List<Issue>> findAllByReporter(String reporter);
    Optional<List<Issue>> findAllById(Integer id);

   Optional<Issue> findByTitle(String title);

}
