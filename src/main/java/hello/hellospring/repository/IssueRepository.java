package hello.hellospring.repository;

import hello.hellospring.Entity.IssueEntity;
import hello.hellospring.Entity.MemberEntity;
import hello.hellospring.Entity.ProjectEntity;
import hello.hellospring.Enum.Priority;
import hello.hellospring.Enum.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
    IssueEntity findByTitle(String title);
    List<IssueEntity> findByMemberId(MemberEntity memberId);
    @Query("SELECT i FROM ISSUE i WHERE i.assignee = :assignee AND i.state = 'ASSIGNED'")
    List<IssueEntity> findIssuesByAssignee(@Param("assignee") String assignee);
    List<IssueEntity> findByProjectId_ProjectNm(String projectNm);
    IssueEntity findByProjectIdAndTitle(ProjectEntity projectId, String title);
}
