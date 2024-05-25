package hello.hellospring.repository;

import hello.hellospring.entity.PriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityEntity, Integer> {
    PriorityEntity findByPriorityNm(String priorityNm); // 우선순위명(중복되지 않는다는 가정 하에)로 우선순위 데이터 조회
}
