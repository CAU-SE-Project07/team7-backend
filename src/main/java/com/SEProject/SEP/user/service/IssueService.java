package com.SEProject.SEP.user.service;

import com.SEProject.SEP.user.domain.Issue;
import com.SEProject.SEP.user.domain.User;
import com.SEProject.SEP.user.dto.IssueDto;
import com.SEProject.SEP.user.dto.UserDto;
import com.SEProject.SEP.user.repository.IssueRepository;
import com.SEProject.SEP.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    @Transactional
    public Integer saveIssue(String userName, IssueDto issueDto) {

        Issue issue = Issue.builder()
                .title(issueDto.getTitle())
                .content(issueDto.getContent())
                .reporter(userName)
                .priority(issueDto.getPriority())
                .state(issueDto.getState())// new가 되도록
                .assignee(issueDto.getAssignee())
                .build();

        issueRepository.save(issue);

        return issue.getId();
    }

    public List<IssueDto> getIssue(String userName) {
        List<Issue> issueList = issueRepository.findAllByReporter(userName).orElseThrow();

        List<IssueDto> issueDtoList = new ArrayList<>();

        for (Issue issue : issueList) {
            IssueDto issueDto = IssueDto.builder()
                    .title(issue.getTitle())
                    .content(issue.getContent())
                    .reporter(issue.getReporter())
                    .priority(issue.getPriority())
                    .state(issue.getState())
                    .assignee(issue.getAssignee())
                    .build();

            issueDtoList.add(issueDto);
        }

        return issueDtoList;

    }
    @Transactional
    public void updateIssue(Integer issueId,IssueDto issueDto) {
        Issue issue = issueRepository.findById(issueId.longValue()).orElseThrow();

        issue.update(Issue.builder()
                .title(issueDto.getTitle())
                .content(issueDto.getContent())
                .reporter(issueDto.getReporter())
                .priority(issueDto.getPriority())
                .state(issueDto.getState())
                .assignee(issueDto.getAssignee())
                .build());
    }

    public Issue getIssueDetail(Integer id){
        Optional<Issue> issue = Optional.of(this.issueRepository.findById(id.longValue()).orElseThrow());


        return issue.get();

    }

}
