package hello.hellospring.controller;

import hello.hellospring.domain.PL;
import hello.hellospring.dto.ProjectLeaderDTO;
import hello.hellospring.service.ProjectLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projectLeaders")
public class ProjectLeaderController {

    @Autowired
    private ProjectLeaderService projectLeaderService;

    @PostMapping
    public ResponseEntity<PL> createProjectLeader(@RequestBody ProjectLeaderDTO projectLeaderDTO) {
        PL createdPL = projectLeaderService.createProjectLeader(projectLeaderDTO);
        return ResponseEntity.ok(createdPL);
    }

    @GetMapping
    public ResponseEntity<List<PL>> getAllProjectLeaders() {
        List<PL> PLS = projectLeaderService.getAllProjectLeaders();
        return ResponseEntity.ok(PLS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PL> getProjectLeaderById(@PathVariable int id) {
        Optional<PL> projectLeader = projectLeaderService.getProjectLeaderById(id);
        return projectLeader.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PL> updateProjectLeader(@PathVariable int id, @RequestBody ProjectLeaderDTO projectLeaderDTO) {
        PL updatedPL = projectLeaderService.updateProjectLeader(id, projectLeaderDTO);
        return ResponseEntity.ok(updatedPL);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectLeader(@PathVariable int id) {
        projectLeaderService.deleteProjectLeader(id);
        return ResponseEntity.noContent().build();
    }
}


