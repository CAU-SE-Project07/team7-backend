package hello.hellospring.repository;

import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Vo.MemberVo;
import hello.hellospring.Vo.ResponseVo;
import hello.hellospring.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberService memberService;
    ProjectEntity project1;

    MemberEntity tester1;
    MemberEntity tester2;
    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        project1 = new ProjectEntity();
        {
            project1.setProjectNm("project1");
            project1.setProjectDesc("this is test project1");
        }
        projectRepository.save(project1);
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

            // tester1 엔티티를 영속화하고 데이터베이스에 즉시 반영
            entityManager.persist(tester1);
            entityManager.flush(); // 플러시를 호출하여 데이터베이스에 즉시 반영합니다.
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

            memberRepository.save(tester2);

        }

    }


    @Test
    public void testfindByUserId(){
        // userId로 멤버를 검색
        MemberEntity found = memberRepository.findByUserId("tester1");

        // 검색된 엔티티가 null이 아니고, 저장된 tester1과 일치하는지 확인
        assertThat(found).isNotNull();
        assertThat(found.getUserId()).isEqualTo(tester1.getUserId());
        assertThat(found.getUserNm()).isEqualTo(tester1.getUserNm());
        assertThat(found.getEmail()).isEqualTo(tester1.getEmail());

    }


    @Test
    void  testfindByUserNm(){
        // userNm으로 멤버를 검색
        MemberEntity found = memberRepository.findByUserNm("tester1");

        // 검색된 엔티티가 null이 아니고, 저장된 tester1과 일치하는지 확인
        assertThat(found).isNotNull();
        assertThat(found.getUserNm()).isEqualTo(tester1.getUserNm());
        assertThat(found.getUserId()).isEqualTo(tester1.getUserId());
        assertThat(found.getEmail()).isEqualTo(tester1.getEmail());
    }

    @Test
    void testLogin()
    {
        MemberVo vo = MemberVo.builder().userId("tester1").userPwd("tester1pass").build();
        ResponseVo responseVo = memberService.login(vo);
        assertThat(responseVo.getCode()).isEqualTo(200);
    }

    @Test
    void testFindByUserId()
    {
        assertThat(memberService.findBymemberId("tester1").getList().getFirst().getUserId()).isEqualTo(tester1.getUserId());
    }

    @Test
    void testSelectAllUsers()
    {
        String a = memberService.selectAllUsers().getList().get(0).getUserId();
        String b = memberService.selectAllUsers().getList().get(1).getUserId();

        assertThat(a).isEqualTo("tester1");
        assertThat(b).isEqualTo("tester2");
    }

    @Test
    void testDeleteUser()
    {
        MemberVo test1 = MemberVo.builder().userId("tester1").build();
        MemberVo test2 = MemberVo.builder().userId("tester2").build();
        List<MemberVo> vos = new ArrayList<>();
        vos.add(test1);
        vos.add(test2);
        assertThat(memberService.deleteUsers(vos).getCode()).isEqualTo(200);
        assertThat(memberService.selectAllUsers().getList().size()).isEqualTo(0);
    }



}
