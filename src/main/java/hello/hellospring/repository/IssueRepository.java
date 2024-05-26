package hello.hellospring.repository;

import hello.hellospring.entity.IssueEntity;
import hello.hellospring.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
    IssueEntity findByTitle(String title);
    List<IssueEntity> findByDescription(String description);
    List<IssueEntity> findByReporter(String reporter);
    List<IssueEntity> findByDate(Date date);
    List<IssueEntity> findByFixer(String fixer);
    List<IssueEntity> findByAssignee(String assignee);
    List<IssueEntity> findByMemberId(MemberEntity memberId);
}
