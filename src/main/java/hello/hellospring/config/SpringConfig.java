package hello.hellospring.config;

import hello.hellospring.repository.*;
import hello.hellospring.service.IssueService;
import hello.hellospring.service.IssueServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final PriorityRepository priorityRepository;
    private final IssueService issueService;
    public SpringConfig(IssueRepository issueRepository, CommentRepository commentRepository, MemberRepository memberRepository,
                        ProjectRepository projectRepository,PriorityRepository priorityRepository, IssueService issueService) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.priorityRepository = priorityRepository;
        this.issueService = issueService;
    }

    @Bean
    public IssueService issueService() {
        return new IssueServiceImpl(issueRepository, commentRepository, memberRepository, projectRepository);
    }
}

