package hello.hellospring.controller;

import hello.hellospring.domain.Developer;
import hello.hellospring.dto.DeveloperDTO;
import hello.hellospring.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;

    //개발자 추가
    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody DeveloperDTO developerDTO) {
        Developer createdDeveloper = developerService.createDeveloper(developerDTO);
        return ResponseEntity.ok(createdDeveloper);
    }
    //모든 개발자 들고오기
    @GetMapping
    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }
    //개발자 정보 끌어오기
    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable int id) {
        Developer developer = developerService.getDeveloperById(id)
                .orElseThrow(() -> new RuntimeException("Developer not found"));
        return ResponseEntity.ok(developer);
    }
    //개발자 업데이트하기
    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable int id, @RequestBody DeveloperDTO developerDTO) {
        Developer updatedDeveloper = developerService.updateDeveloper(id, developerDTO);
        return ResponseEntity.ok(updatedDeveloper);
    }
    //개발자 지우기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable int id) {
        developerService.deleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }
}

