package hello.hellospring.controller;

import hello.hellospring.service.CommentService;
import hello.hellospring.vo.CommentReqVo;
import hello.hellospring.vo.CommentResVo;
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
    public ResponseVo addComment(@RequestBody CommentReqVo commentReqVo) {
        return commentService.addComment(commentReqVo);
    }


    /**
     * MyBatis - 코멘트 추가
     * */
    @PostMapping("/insertComment")
    public ResponseVo insertComment(@RequestBody CommentReqVo commentReqVo) {
        return commentService.insertComment(commentReqVo);
    }


}


