package hello.hellospring.service;

import hello.hellospring.domain.Developer;
import hello.hellospring.domain.PL;
import hello.hellospring.domain.Tester;
import hello.hellospring.repository.DeveloperRepository;
import hello.hellospring.repository.ProjectLeaderRepository;
import hello.hellospring.repository.TesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements  MemberService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private ProjectLeaderRepository projectLeaderRepository;

    @Autowired
    private TesterRepository testerRepository;

    public boolean authenticateMember(String userId, String password) {
        Optional<Developer> developer = developerRepository.findByUserId(userId);
        if (developer.isPresent() && developer.get().getPassword().equals(password)) {
            return true;
        }

        Optional<PL> projectLeader = projectLeaderRepository.findByUserId(userId);
        if (projectLeader.isPresent() && projectLeader.get().getPassword().equals(password)) {
            return true;
        }

        Optional<Tester> tester = testerRepository.findByUserId(userId);
        return tester.isPresent() && tester.get().getPassword().equals(password);
    }
}