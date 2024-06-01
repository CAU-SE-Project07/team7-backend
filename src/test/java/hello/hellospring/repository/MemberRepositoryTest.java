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
class MemberRepositoryTest {
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



}
