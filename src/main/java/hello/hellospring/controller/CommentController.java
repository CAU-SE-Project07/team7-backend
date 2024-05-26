package hello.hellospring.controller;

import hello.hellospring.service.CommentService;
import hello.hellospring.vo.CommentVo;
import hello.hellospring.vo.ResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    public ResponseVo createComment(@RequestBody CommentVo commentVo) {
        return commentService.insertComment(commentVo);
    }
}
