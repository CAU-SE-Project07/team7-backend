package hello.hellospring.repository;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestEntityManager entityManager;

    private IssueEntity issue1;
    private IssueEntity issue2;
    private IssueEntity issue3;
    private IssueEntity issue4;
    private IssueEntity issue5;
    ProjectEntity project1;
    MemberEntity tester1;
    MemberEntity tester2;

    @BeforeEach
    void setUp()
    {
        project1 = new ProjectEntity();
        {
            project1.setProjectId(1);
            project1.setProjectNm("project1");
            project1.setProjectDesc("this is test project1");
        }
        tester1 = new MemberEntity();
        {
            tester1.setMemberId(1);
            tester1.setUserId("tester1");
            tester1.setUserNm("tester1");
            tester1.setUserPwd("tester1pass");
            tester1.setUserChkPwd("tester1pass");
            tester1.setUserRoles("TESTER");
            tester1.setNickNm("tes1");
            tester1.setEmail("tester1@gmail.com");
            tester1.setProjectId(project1);
        }
        tester2 = new MemberEntity();
        {
            tester2.setMemberId(2);
            tester2.setUserId("tester2");
            tester2.setUserNm("tester2");
            tester2.setUserPwd("tester2pass");
            tester2.setUserChkPwd("tester2pass");
            tester2.setUserRoles("TESTER");
            tester2.setNickNm("tes2");
            tester2.setEmail("tester2@gmail.com");
            tester2.setProjectId(project1);
        }
        issue1 = new IssueEntity();
        {
            issue1.setIssueId(1);
            issue1.setTitle("Issue1");
            issue1.setDescription("This is test issue1");
            issue1.setReporter("");
            issue1.setAssignee("");
            issue1.setDate("2025/05/29");
            issue1.setPriority("MAJOR");
            issue1.setProjectId(project1);
            issue1.setMemberId(tester1);
        }
        issue2 = new IssueEntity();
        {
            issue2.setIssueId(2);
            issue2.setTitle("Issue2");
            issue2.setDescription("This is test issue2");
            issue2.setReporter("");
            issue2.setAssignee("");
            issue2.setDate("2025/05/29");
            issue2.setPriority("MAJOR");
            issue2.setProjectId(project1);
            issue2.setMemberId(tester2);
        }
        issue3 = new IssueEntity();
        {
            issue3.setIssueId(3);
            issue3.setTitle("Issue3");
            issue3.setDescription("This is test issue3");
            issue3.setReporter("");
            issue3.setAssignee("");
            issue3.setDate("2025/05/29");
            issue3.setPriority("MAJOR");
            issue3.setProjectId(project1);
            issue3.setMemberId(tester2);
        }
        issue4 = new IssueEntity();
        {
            issue4.setIssueId(4);
            issue4.setTitle("Issue4");
            issue4.setDescription("This is test issue4");
            issue4.setReporter("");
            issue4.setAssignee("hojin1");
            issue4.setDate("2025/05/29");
            issue4.setState("ASSIGNED");
            issue4.setPriority("MAJOR");
            issue4.setProjectId(project1);
            issue4.setMemberId(null);
        }
        issue5 = new IssueEntity();
        {
            issue5.setIssueId(5);
            issue5.setTitle("Issue5");
            issue5.setDescription("This is test issue5");
            issue5.setReporter("");
            issue5.setAssignee("hojin1");
            issue5.setDate("2025/05/29");
            issue5.setState("ASSIGNED");
            issue5.setPriority("MAJOR");
            issue5.setProjectId(project1);
            issue5.setMemberId(null);
        }
        List<MemberEntity> memberEntities = new ArrayList<>();
        memberEntities.add(tester1);
        memberEntities.add(tester2);
        project1.setMemberEntities(memberEntities);
        entityManager.persist(project1);
        entityManager.persist(tester1);
        entityManager.persist(tester2);
        projectRepository.save(project1);
        memberRepository.save(tester1);
        memberRepository.save(tester2);
        issueRepository.save(issue1);
        issueRepository.save(issue2);
        issueRepository.save(issue3);
        issueRepository.save(issue4);
        issueRepository.save(issue5);
    }

    @Test
    public void testFindByTitle()
    {
        Assertions.assertThat(issueRepository.findByTitle("Issue1")).isEqualTo(issue1);
        Assertions.assertThat(issueRepository.findByTitle("Issue2")).isEqualTo(issue2);
    }

    @Test
    public void testFindByMemberId()
    {
        List<IssueEntity> issueEntities1 = new ArrayList<>();
        issueEntities1.add(issue1);
        List<IssueEntity> issueEntities2 = new ArrayList<>();
        issueEntities2.add(issue2);
        issueEntities2.add(issue3);
        Assertions.assertThat(issueRepository.findByMemberId(tester1)).isEqualTo(issueEntities1);
        Assertions.assertThat(issueRepository.findByMemberId(tester2)).isEqualTo(issueEntities2);
    }

    @Test
    public void testFindIssuesByAssignee()
    {
        List<IssueEntity> issueEntities1 = new ArrayList<>();
        issueEntities1.add(issue4);
        issueEntities1.add(issue5);
        Assertions.assertThat(issueRepository.findIssuesByAssignee("hojin1")).isEqualTo(issueEntities1);
    }
}
