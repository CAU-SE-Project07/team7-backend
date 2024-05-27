package hello.hellospring.service;

import hello.hellospring.domain.Developer;
import hello.hellospring.dto.DeveloperDTO;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {
    public Developer createDeveloper(DeveloperDTO developerDTO);
    public List<Developer> getAllDevelopers();
    public Optional<Developer> getDeveloperById(int id);
    public Developer updateDeveloper(int id, DeveloperDTO developerDTO);
    public void deleteDeveloper(int id);
}
