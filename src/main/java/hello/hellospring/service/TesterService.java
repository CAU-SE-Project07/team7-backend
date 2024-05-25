package hello.hellospring.service;

import hello.hellospring.domain.Tester;
import hello.hellospring.dto.TesterDTO;

import java.util.List;
import java.util.Optional;

public interface TesterService {
    public Tester createTester(TesterDTO testerDTO);
    public List<Tester> getAllTesters();
    public Optional<Tester> getTesterById(int id);
    public Tester updateTester(int id, TesterDTO testerDTO);
    public void deleteTester(int id);
}
