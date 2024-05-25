package hello.hellospring.repository;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.ProjectLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLeaderRepository extends JpaRepository<ProjectLeader, Integer> {
}
