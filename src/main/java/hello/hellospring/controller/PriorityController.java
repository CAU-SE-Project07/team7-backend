package hello.hellospring.controller;

import hello.hellospring.vo.PriorityVo;
import hello.hellospring.vo.ResponseVo;
import hello.hellospring.service.PriorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/priority")
@RequiredArgsConstructor
public class PriorityController {
    private final PriorityService priorityService;

    /**
     * 우선순위 추가
     * */
    @PostMapping("/addPriority")
    public ResponseVo insertPriority(@RequestBody PriorityVo priorityVo) {
        return priorityService.insertPriority(priorityVo);
    }

    /**
     * 우선순위 변경
     * */
    @PostMapping("/updatePriority")
    public ResponseVo updatePriority(@RequestBody PriorityVo priorityVo) {
        return priorityService.updatePriority(priorityVo);
    }
}
