package hello.hellospring.repository;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.Tester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesterRepository extends JpaRepository<Tester, Integer> {
}