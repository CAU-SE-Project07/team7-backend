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

    /**
     * MyBatis - 코멘트 추가
     * */
    @PostMapping("/01")
    public ResponseVo addComment(@RequestBody CommentReqVo commentReqVo) {
        return commentService.addComment(commentReqVo);
    }

    /**
     * 코멘트 수정
     * */
    @PostMapping("/02")
    public ResponseVo updateComment(@RequestBody CommentReqVo commentReqVo) {
        return commentService.updateComment(commentReqVo);
    }


}


