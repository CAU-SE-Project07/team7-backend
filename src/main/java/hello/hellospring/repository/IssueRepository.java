package hello.hellospring.repository;

import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
    IssueEntity findByTitle(String title);
    List<IssueEntity> findByReporter(String userId);
    List<IssueEntity> findByMemberId(int memberId);

}
