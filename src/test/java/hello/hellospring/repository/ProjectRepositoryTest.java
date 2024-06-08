package hello.hellospring.repository;

import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TestEntityManager entityManager;

    private ProjectEntity project1;
    private ProjectEntity project2;
    private MemberEntity member1;

    @BeforeEach
    void setUp() {
        project1 = new ProjectEntity();
        project1.setProjectId(1);
        project1.setProjectNm("Project1");
        project1.setProjectDesc("This is project 1");
        entityManager.persist(project1);

        project2 = new ProjectEntity();
        project2.setProjectId(2);
        project2.setProjectNm("Project2");
        project2.setProjectDesc("This is project 2");
        entityManager.persist(project2);

        member1 = new MemberEntity();
        member1.setMemberId(1);
        member1.setUserNm("Test User");
        member1.setProjectId(project1);
        entityManager.persist(member1);
    }

    @Test
    public void testFindByProjectNm() {
        ProjectEntity foundProject1 = projectRepository.findByProjectNm("Project1");
        Assertions.assertThat(foundProject1).isEqualTo(project1);
        ProjectEntity foundProject2 = projectRepository.findByProjectNm("Project2");
        Assertions.assertThat(foundProject2).isEqualTo(project2);
    }
}
