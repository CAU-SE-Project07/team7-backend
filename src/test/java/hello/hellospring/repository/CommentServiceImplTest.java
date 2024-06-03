package hello.hellospring.repository;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.CommentVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.CommentService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class CommentServiceImplTest {
    @Autowired
    CommentService commentService;
    ProjectEntity project1;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    MemberRepository memberRepository;
    MemberEntity tester1;
    @Autowired
    IssueRepository issueRepository;
    IssueEntity issue1;
    @BeforeEach
    void setUp() {
        project1 = new ProjectEntity();
        {
            project1.setProjectNm("testProjectName");
            project1.setProjectDesc("this is test project1");
        }
        projectRepository.save(project1);
        tester1 = new MemberEntity();
        {
            tester1.setUserId("testID");
            tester1.setUserNm("testID");
            tester1.setUserPwd("tester1pass");
            tester1.setUserChkPwd("tester1pass");
            tester1.setUserRoles("TESTER");
            tester1.setNickNm("tes1");
            tester1.setEmail("tester1@gmail.com");
            tester1.setProjectId(project1);
        }
        memberRepository.save(tester1);
        issue1 = new IssueEntity();
        {
            issue1.setTitle("testTitle");
            issue1.setDescription("This is test issue1");
            issue1.setReporter("");
            issue1.setAssignee("");
            issue1.setDate("2025/05/29");
            issue1.setPriority("MAJOR");
            issue1.setProjectId(project1);
            issue1.setMemberId(tester1);
        }
        issueRepository.save(issue1);

    }
    @Test
    public void insertCommentTest()
    {
        String cont = "this is test content";
        String dat = "2024/06/01";
        String usId = "testID";
        String title = "testTitle";
        String projectName = "testProjectName";
        CommentVo commentVo = CommentVo.builder().
                content(cont).
                date(dat).
                userId(usId).
                issueTitle(title).
                projectNm(projectName).build();
        ResponseVo response = commentService.insertComment(commentVo);

        Assertions.assertThat(response.getMsg()).isEqualTo("SUCCESS");
    }
}
