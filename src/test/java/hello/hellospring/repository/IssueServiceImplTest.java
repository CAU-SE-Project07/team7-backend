package hello.hellospring.repository;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.IssueCreateVo;
import hello.hellospring.Vo.IssueUpdateVo;
import hello.hellospring.Vo.IssueVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.IssueService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class IssueServiceImplTest {
    @Autowired
    private IssueService issueService;
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager entityManager;

    ProjectEntity project1;
    MemberEntity tester1;
    MemberEntity tester2;
    MemberEntity developer1;
    IssueEntity issue1;
    IssueEntity issue2;
    IssueEntity issue3;
    IssueEntity issue4;

    @BeforeEach
    void setUp()
    {
        project1 = new ProjectEntity();
        {
            project1.setProjectNm("project1");
            project1.setProjectDesc("this is test project1");
        }
        tester1 = new MemberEntity();
        {
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
            tester2.setUserId("tester2");
            tester2.setUserNm("tester2");
            tester2.setUserPwd("tester2pass");
            tester2.setUserChkPwd("tester2pass");
            tester2.setUserRoles("TESTER");
            tester2.setNickNm("tes2");
            tester2.setEmail("tester2@gmail.com");
            tester2.setProjectId(project1);
        }
        developer1 = new MemberEntity();
        {
            developer1.setUserId("developer1");
            developer1.setUserNm("developer1");
            developer1.setUserPwd("dev1pass");
            developer1.setUserChkPwd("dev1pass");
            developer1.setUserRoles("DEVELOPER");
            developer1.setNickNm("dev1");
            developer1.setEmail("developer1@gmail.com");
            developer1.setProjectId(project1);
        }

        List<MemberEntity> memberEntities = new ArrayList<>();
        memberEntities.add(tester1);
        memberEntities.add(tester2);
        project1.setMemberEntities(memberEntities);
//        entityManager.persist(project1);
//        entityManager.persist(tester1);
//        entityManager.persist(tester2);
        projectRepository.save(project1);
        memberRepository.save(tester1);
        memberRepository.save(tester2);
        memberRepository.save(developer1);

        issue1 = new IssueEntity();
        issue1.setTitle("Issuetest");
        issue1.setDescription("This is test issue1");
        issue1.setReporter("reporter1");
        issue1.setDate("2025/05/29");
        issue1.setAssignee("Developer1");
        issue1.setPriority("MAJOR");
        issue1.setState("OPEN");
        issue1.setProjectId(project1);
        issue1.setMemberId(tester1);
        issueRepository.save(issue1);

        issue2 = new IssueEntity();
        issue2.setTitle("updateToFixed");
        issue2.setDescription("This is test toFixed");
        issue2.setReporter("reporter1");
        issue2.setDate("2025/05/29");
        issue2.setAssignee("Developer1");
        issue2.setPriority("MAJOR");
        issue2.setState("ASSIGNED");
        issue2.setProjectId(project1);
        issue2.setMemberId(tester1);
        issueRepository.save(issue2);

        issue3 = new IssueEntity();
        issue3.setTitle("updateToFixed2");
        issue3.setDescription("This is test toFixed");
        issue3.setReporter("reporter2");
        issue3.setDate("2025/05/29");
        issue3.setAssignee("Developer1");
        issue3.setPriority("MAJOR");
        issue3.setState("ASSIGNED");
        issue3.setProjectId(project1);
        issue3.setMemberId(tester1);
        issueRepository.save(issue3);

        issue4 = new IssueEntity();
        issue4.setTitle("updateToFixed2");
        issue4.setDescription("This is test toFixed");
        issue4.setReporter("awef");
        issue4.setDate("2025/05/29");
        issue4.setAssignee("awef");
        issue4.setPriority("MAJOR");
        issue4.setState("REOPENED");
        issue4.setProjectId(project1);
        issue4.setMemberId(tester1);
        issueRepository.save(issue4);
    }


    @Test
    void testInsertIssue()
    {
        String l_title = "Issue create test issue1";
        String l_des = "this is Issue create test1";
        String l_date = "2024/05/29";
        String l_pri = "MAJOR";
        IssueCreateVo issueCreateVo = IssueCreateVo.builder().
                title(l_title).
                description(l_des).
                date(l_date).
                priority(l_pri).
                userId(tester1.getUserId())
                .build();

        assertThat(issueService.insertIssue(issueCreateVo).getMsg()).isEqualTo("SUCCESS");

        assertThat(issueRepository.findByTitle("Issue create test issue1").getTitle()).isEqualTo("Issue create test issue1");
    }

   @Test
   void testUpdateIssueWithAssignee()
   {
       String l_title = "Issue create test issue1";
       String l_des = "this is Issue create test1";
       String l_date = "2024/05/29";
       String l_pri = "MAJOR";
       IssueCreateVo issueCreateVo = IssueCreateVo.builder().
               title(l_title).
               description(l_des).
               date(l_date).
               priority(l_pri).
               userId(tester1.getUserId()).
               build();
       issueService.insertIssue(issueCreateVo);

       IssueUpdateVo issueUpdateVo = IssueUpdateVo.builder().
               title(l_title).
               description("").
               date("").
               assignee(developer1.getUserId()).
               priority("").
               state("").
               build();

       assertThat(issueService.updateIssue(issueUpdateVo).getMsg()).isEqualTo("SUCCESS");
       IssueEntity updatedIssue = issueRepository.findByTitle(l_title);
       //제목 안바뀌는지 확인
       assertThat(updatedIssue.getTitle()).isEqualTo(l_title);
       //내용 안바뀌는지 확인
       assertThat(updatedIssue.getDescription()).isEqualTo(l_des);
       //날짜 안바뀌는지 확인
       assertThat(updatedIssue.getDate()).isEqualTo(l_date);
       //Priority안바뀌는지 확인
       assertThat(updatedIssue.getPriority()).isEqualTo(l_pri);
       //state Assigned로 바뀌는지 확인
       assertThat(updatedIssue.getState()).isEqualTo("ASSIGNED");
       //Assignee 제대로 바뀌는지 확인
       assertThat(updatedIssue.getAssignee()).isEqualTo("developer1");
   }

    @Test
    public void testUpdateDescriptionOnly()
    {
        String originalTitle = issue1.getTitle();
        String originalReporter = issue1.getReporter();
        String originalDate = issue1.getDate();
        String originalPriority = issue1.getPriority();
        String originalState = issue1.getState();
        String originalAssignee = issue1.getAssignee();

        IssueUpdateVo updateVo = IssueUpdateVo.builder()
                .title(originalTitle)
                .description("Updated Description")
                .date(originalDate)
                .assignee(originalAssignee)
                .priority(originalPriority)
                .state(originalState)
                .build();

        assertThat(issueService.updateIssue(updateVo).getMsg()).isEqualTo("SUCCESS");

        IssueEntity updatedIssue = issueRepository.findById(issue1.getIssueId()).orElse(null);
        assertThat(updatedIssue).isNotNull();
        assertThat(updatedIssue.getTitle()).isEqualTo(originalTitle);
        assertThat(updatedIssue.getDescription()).isEqualTo("Updated Description");
        assertThat(updatedIssue.getReporter()).isEqualTo(originalReporter);
        assertThat(updatedIssue.getDate()).isEqualTo(originalDate);
        assertThat(updatedIssue.getPriority()).isEqualTo(originalPriority);
        assertThat(updatedIssue.getState()).isEqualTo(originalState);
        assertThat(updatedIssue.getAssignee()).isEqualTo(originalAssignee);
    }

    @Test
    public void testUpdatePriorityOnly()
    {
        String originalTitle = issue1.getTitle();
        String originalDescription = issue1.getDescription();
        String originalReporter = issue1.getReporter();
        String originalDate = issue1.getDate();
        String originalState = issue1.getState();
        String originalAssignee = issue1.getAssignee();

        IssueUpdateVo updateVo = IssueUpdateVo.builder()
                .title(originalTitle)
                .description(originalDescription)
                .date(originalDate)
                .assignee(originalAssignee)
                .priority("MINOR")
                .state(originalState)
                .build();

        assertThat(issueService.updateIssue(updateVo).getMsg()).isEqualTo("SUCCESS");

        IssueEntity updatedIssue = issueRepository.findById(issue1.getIssueId()).orElse(null);
        assertThat(updatedIssue).isNotNull();
        assertThat(updatedIssue.getTitle()).isEqualTo(originalTitle);
        assertThat(updatedIssue.getDescription()).isEqualTo(originalDescription);
        assertThat(updatedIssue.getReporter()).isEqualTo(originalReporter);
        assertThat(updatedIssue.getDate()).isEqualTo(originalDate);
        assertThat(updatedIssue.getPriority()).isEqualTo("MINOR");
        assertThat(updatedIssue.getState()).isEqualTo(originalState);
        assertThat(updatedIssue.getAssignee()).isEqualTo(originalAssignee);
    }

    @Test
    public void testUpdateToFixed()
    {
        IssueUpdateVo updateVo = IssueUpdateVo.builder()
                .title(issue2.getTitle())
                .description("")
                .date("")
                .assignee("")
                .priority("MINOR")
                .state("FIXED")
                .build();
        assertThat(issueService.updateIssue(updateVo).getMsg()).isEqualTo("SUCCESS");
        IssueEntity updatedIssue = issueRepository.findByTitle(issue2.getTitle());
        assertThat(updatedIssue.getFixer()).isEqualTo(issue2.getAssignee());
        assertThat(updatedIssue.getState()).isEqualTo("FIXED");
    }

    @Test
    void testGetIssuesByAssigne(){
        List<IssueVo> vos = new ArrayList<>();
        vos.add(issueService.convertToVo(issue2));
        vos.add(issueService.convertToVo(issue3));
        assertThat(issueService.getIssuesByAssignee("Developer1")).isEqualTo(vos);

    }

    @Test
    void testGetAllIssues()
    {
        List<IssueVo> vos = new ArrayList<>();
        vos.add(issueService.convertToVo(issue1));
        vos.add(issueService.convertToVo(issue2));
        vos.add(issueService.convertToVo(issue3));
        vos.add(issueService.convertToVo(issue4));
        assertThat(issueService.getAllIssues()).isEqualTo(vos);
    }

    @Test
    void testGetIssuesByReporter()
    {
        List<IssueVo> vos = new ArrayList<>();
        vos.add(issueService.convertToVo(issue1));
        vos.add(issueService.convertToVo(issue2));
        assertThat(issueService.getIssuesByReporter("reporter1")).isEqualTo(vos);
    }

    @Test
    void testGetIssueByTitle()
    {
        assertThat(issueService.getIssueByTitle(issue1.getTitle()).getList().getFirst().getTitle()).isEqualTo(issue1.getTitle());
    }

    @Test
    void testGetIssuesByStates()
    {
        List<String> states = new ArrayList<>();
        states.add("OPEN");
        states.add("ASSIGNED");
        List<IssueVo> vos = new ArrayList<>();
        vos.add(issueService.convertToVo(issue1));
        vos.add(issueService.convertToVo(issue2));
        vos.add(issueService.convertToVo(issue3));
        assertThat(issueService.getIssuesByStates(states)).isEqualTo(vos);
    }

}
