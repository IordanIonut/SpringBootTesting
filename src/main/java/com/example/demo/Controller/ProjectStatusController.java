package com.example.demo.Controller;

import com.example.demo.Model.ProjectStatus;
import com.example.demo.Service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/project/status")
public class ProjectStatusController {
    @Autowired
    public ProjectStatusService projectStatusService;
    @PostMapping("/add")
    public ResponseEntity<ProjectStatus> saveProjectStatus(@RequestBody ProjectStatus projectStatus) {
        ProjectStatus save = projectStatusService.saveProjectStatus(projectStatus);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<ProjectStatus>> getProjectStatus(){
        List<ProjectStatus> projectStatus = projectStatusService.getProjectStatus();
        return ResponseEntity.ok(projectStatus);
    }
    @GetMapping("/id")
    public ResponseEntity<List<ProjectStatus>> getProjectStatusById(@RequestParam Long id){
            Optional<ProjectStatus> projectStatus = projectStatusService.getProjectStatusById(id);
        return projectStatus.map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectStatus> updateProjectStatus(@PathVariable Long id, @RequestBody ProjectStatus updatedProjectStatus) {
        ProjectStatus result = projectStatusService.updateProjectStatus(id, updatedProjectStatus);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProjectStatus(@PathVariable Long id) {
        projectStatusService.deleteProjectStatus(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
