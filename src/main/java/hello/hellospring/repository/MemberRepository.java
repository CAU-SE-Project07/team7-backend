package hello.hellospring.repository;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    MemberEntity findByUserId(String userId);
    MemberEntity findByUserNm(String userNm);
}
