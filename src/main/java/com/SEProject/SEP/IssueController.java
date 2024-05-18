package com.SEProject.SEP;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class IssueController {
    private final IssueRepository issueRepository;

    @GetMapping("/issue/list")
    public String list(Model model) {
        List<Issue> issueList = this.issueRepository.findAll();
        model.addAttribute("issueList", issueList);
        return "issue_list";
    }
}
