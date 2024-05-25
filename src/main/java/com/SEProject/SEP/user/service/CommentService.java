package com.SEProject.SEP.user.service;


import com.SEProject.SEP.user.domain.Comment;
import com.SEProject.SEP.user.dto.CommentDto;
import com.SEProject.SEP.user.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Integer saveComment(String userName, CommentDto commentDto){

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .writer(userName)
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }
}
