package hello.hellospring.service;

import hello.hellospring.vo.PriorityVo;
import hello.hellospring.vo.ResponseVo;

public interface PriorityService {
    ResponseVo insertPriority(PriorityVo priorityVo);
    ResponseVo updatePriority(PriorityVo priorityVo);
}