package hello.hellospring.controller;

import hello.hellospring.domain.Tester;
import hello.hellospring.dto.TesterDTO;
import hello.hellospring.service.TesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/testers")
public class TesterController {

    @Autowired
    private TesterService testerService;

    @PostMapping
    public ResponseEntity<Tester> createTester(@RequestBody TesterDTO testerDTO) {
        Tester createdTester = testerService.createTester(testerDTO);
        return ResponseEntity.ok(createdTester);
    }

    @GetMapping
    public List<Tester> getAllTesters() {
        return testerService.getAllTesters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tester> getTesterById(@PathVariable int id) {
        Tester tester = testerService.getTesterById(id)
                .orElseThrow(() -> new RuntimeException("Tester not found"));
        return ResponseEntity.ok(tester);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tester> updateTester(@PathVariable int id, @RequestBody TesterDTO testerDTO) {
        Tester updatedTester = testerService.updateTester(id, testerDTO);
        return ResponseEntity.ok(updatedTester);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTester(@PathVariable int id) {
        testerService.deleteTester(id);
        return ResponseEntity.noContent().build();
    }
}

