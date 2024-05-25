package com.SEProject.SEP.user.controller;

import com.SEProject.SEP.user.dto.CommentDto;
import com.SEProject.SEP.user.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userName}/comment/save")
    public ResponseEntity<Integer> saveComment(@PathVariable("userName") String userName,
                                               @RequestBody CommentDto commentDto){
        Integer commentId = commentService.saveComment(userName, commentDto);
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }
}
