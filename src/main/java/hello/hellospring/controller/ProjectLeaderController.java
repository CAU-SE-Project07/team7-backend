package hello.hellospring.controller;

import hello.hellospring.domain.ProjectLeader;
import hello.hellospring.dto.ProjectLeaderDTO;
import hello.hellospring.service.ProjectLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-leaders")
public class ProjectLeaderController {

    @Autowired
    private ProjectLeaderService projectLeaderService;

    @PostMapping
    public ResponseEntity<ProjectLeader> createProjectLeader(@RequestBody ProjectLeaderDTO projectLeaderDTO) {
        ProjectLeader createdProjectLeader = projectLeaderService.createProjectLeader(projectLeaderDTO);
        return ResponseEntity.ok(createdProjectLeader);
    }

    @GetMapping
    public List<ProjectLeader> getAllProjectLeaders() {
        return projectLeaderService.getAllProjectLeaders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectLeader> getProjectLeaderById(@PathVariable int id) {
        ProjectLeader projectLeader = projectLeaderService.getProjectLeaderById(id)
                .orElseThrow(() -> new RuntimeException("ProjectLeader not found"));
        return ResponseEntity.ok(projectLeader);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectLeader> updateProjectLeader(@PathVariable int id, @RequestBody ProjectLeaderDTO projectLeaderDTO) {
        ProjectLeader updatedProjectLeader = projectLeaderService.updateProjectLeader(id, projectLeaderDTO);
        return ResponseEntity.ok(updatedProjectLeader);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectLeader(@PathVariable int id) {
        projectLeaderService.deleteProjectLeader(id);
        return ResponseEntity.noContent().build();
    }
}


