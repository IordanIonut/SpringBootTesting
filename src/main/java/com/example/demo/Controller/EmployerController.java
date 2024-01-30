package com.example.demo.Controller;

import com.example.demo.Model.Employer;
import com.example.demo.Service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/employee")
public class EmployerController {
    @Autowired
    public EmployerService employerService;

    @PostMapping("/add")
    public ResponseEntity<Object> saveEmployer(@RequestBody Employer employer) {
        try {
            Employer save = employerService.saveEmployer(employer);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Email address already exists.", HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getEmployers() {
        List<Employer> employers = employerService.getEmployers();
        return ResponseEntity.ok(employers);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Employer>> getEmployerById(@RequestParam Long id) {
        Optional<Employer> employer = employerService.getEmployerById(id);
        return employer.map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(@PathVariable Long id, @RequestBody Employer updatedEmployer) {
        Employer result = employerService.updateEmployer(id, updatedEmployer);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployer(@PathVariable("id") Long id) {
        employerService.deleteEmployer(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

}
