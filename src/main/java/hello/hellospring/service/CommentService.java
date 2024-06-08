package hello.hellospring.service;

import hello.hellospring.Vo.CommentVo;
import hello.hellospring.Vo.PriorityVo;
import hello.hellospring.Vo.ResponseVo;

public interface CommentService {
    public ResponseVo insertComment(CommentVo commentVo);
}