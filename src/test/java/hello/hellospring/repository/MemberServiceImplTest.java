package hello.hellospring.repository;

import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestEntityManager entityManager;

    ProjectEntity project1;

    MemberEntity tester1;
    MemberEntity tester2;

    @BeforeEach
    void setUp() {

        tester1 = new MemberEntity();{
            tester1.setMemberId(1);
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

    }

    @Test
    void testinsertMember(){
        MemberEntity newMember = new MemberEntity();
        newMember.setMemberId(3);
        newMember.setUserId("tester3");
        newMember.setUserNm("tester3");
        newMember.setUserPwd("tester3pass");
        newMember.setUserChkPwd("tester3pass");
        newMember.setUserRoles("TESTER");
        newMember.setNickNm("tes3");
        newMember.setEmail("tester3@gmail.com");
        newMember.setProjectId(project1);

        // 멤버를 저장
        memberRepository.save(newMember);

        // 저장된 멤버를 검색
        MemberEntity found = memberRepository.findByUserId("tester3");

        // 검색된 엔티티가 null이 아니고, 저장된 newMember와 일치하는지 확인
        assertThat(found).isNotNull();
        assertThat(found.getUserId()).isEqualTo(newMember.getUserId());
        assertThat(found.getUserNm()).isEqualTo(newMember.getUserNm());
        assertThat(found.getEmail()).isEqualTo(newMember.getEmail());


    }


    @Test
    void testUpdateMember(){
        tester1.setUserNm("updatedName");
        tester1.setEmail("updatedemail@gmail.com");

        memberRepository.save(tester1);

        MemberEntity updatedMember = memberRepository.findByUserId("tester1");

        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.getUserNm()).isEqualTo("updatedName");
        assertThat(updatedMember.getEmail()).isEqualTo("updatedemail@gmail.com");

    }

    @Test
    void testlogin(){

        String userId = "tester1";
        String password = "tester1pass";

        MemberEntity found = memberRepository.findByUserId(userId);

        assertThat(found).isNotNull();
        assertThat(found.getUserPwd()).isEqualTo(password);

    }

}
