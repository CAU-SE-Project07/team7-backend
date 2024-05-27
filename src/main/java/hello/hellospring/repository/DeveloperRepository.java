package hello.hellospring.repository;

import hello.hellospring.domain.Comment;
import hello.hellospring.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findByUserId(String userId);
}
