package hello.hellospring.service;

import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.PriorityVo;
import hello.hellospring.Vo.ResponseVo;

public interface PriorityService {
    ResponseVo insertPriority(PriorityVo priorityVo);
    ResponseVo updatePriority(PriorityVo priorityVo);
}