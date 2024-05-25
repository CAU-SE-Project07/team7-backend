package com.SEProject.SEP.user.repository;

import com.SEProject.SEP.user.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
