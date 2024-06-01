package hello.hellospring.repository;

import hello.hellospring.Entity.PriorityEntity;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback(true)
public class PriorityRepositoryTest {

    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PriorityEntity priority1;
    private PriorityEntity priority2;

    @BeforeEach
    void setUp() {
        // 우선순위 엔티티 생성
        priority1 = new PriorityEntity();
        {
            priority1.setPriorityNm("Major");
            priority1.setLevel(1);

            entityManager.persist(priority1);
            entityManager.flush();
        }
        priority2 = new PriorityEntity();
        {
            priority2.setPriorityNm("minor");
            priority2.setLevel(1);

            entityManager.persist(priority2);
            entityManager.flush();
        }
    }




    @Test
    public void testFindByPriorityNm() {
        PriorityEntity foundPriority1 = priorityRepository.findByPriorityNm("Major");
        Assertions.assertThat(foundPriority1).isEqualTo(priority1);
        PriorityEntity foundPriority2 = priorityRepository.findByPriorityNm("minor");
        Assertions.assertThat(foundPriority2).isEqualTo(priority2);
    }
}
