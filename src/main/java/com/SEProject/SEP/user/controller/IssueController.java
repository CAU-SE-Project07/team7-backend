package com.SEProject.SEP.user.controller;

import com.SEProject.SEP.user.dto.IssueDto;
import com.SEProject.SEP.user.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    @PostMapping("/{userName}/issue/save")
    public ResponseEntity<Integer> saveIssue(@PathVariable("userName") String userName, @RequestBody IssueDto issueDto){
        Integer issueId = issueService.saveIssue(userName,issueDto);
        return new ResponseEntity<>(issueId, HttpStatus.OK);
    }

   // @GetMapping("/issue/list")
   // public String

    @GetMapping("/{userName}/issue")
    public ResponseEntity<List<IssueDto>> findIssue(@PathVariable("userName") String userName) {
        return new ResponseEntity<>(issueService.getIssue(userName),HttpStatus.OK);
    }

    @PutMapping("/{issueId}/issue")
    public ResponseEntity<String> findIssue(@PathVariable("issueId") Integer issueId, @RequestBody IssueDto issueDto) {
        issueService.updateIssue(issueId,issueDto);
        return new ResponseEntity<>("수정 성공",HttpStatus.OK);
    }


    @GetMapping("/detail/{title}")
    public ResponseEntity<IssueDto> getIssueDetail(@PathVariable("title")String title){
        return new ResponseEntity<>(issueService.getIssueDetail(title),HttpStatus.OK);
    }

}
