package hello.hellospring.repository;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
}
