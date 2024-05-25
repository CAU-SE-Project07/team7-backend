package hello.hellospring.service;

import hello.hellospring.vo.CommentVo;
import hello.hellospring.vo.ResponseVo;

public interface CommentService {
    ResponseVo insertComment(CommentVo commentVo);
}