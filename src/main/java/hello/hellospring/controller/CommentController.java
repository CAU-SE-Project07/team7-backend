package hello.hellospring.controller;

import hello.hellospring.Vo.CommentVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseVo createComment(@RequestBody CommentVo commentVo) {
        return commentService.insertComment(commentVo);
    }
}
