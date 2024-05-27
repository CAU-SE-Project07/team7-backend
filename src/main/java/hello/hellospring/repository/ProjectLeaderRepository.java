package hello.hellospring.repository;

import hello.hellospring.domain.PL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectLeaderRepository extends JpaRepository<PL, Integer> {
    Optional<PL> findByUserId(String userId);
}
