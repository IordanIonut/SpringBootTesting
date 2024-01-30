package com.example.demo.Controller;

import com.example.demo.Model.Project;
import com.example.demo.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    public ProjectService projectService;
    @PostMapping("/add")
    public ResponseEntity<Project> saveProject(@RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Project>> getProjects(){
        List<Project> projects = projectService.getProjects();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/id")
    public ResponseEntity<List<Project>> getEmployerById(@RequestParam Long id){
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateEmployer(@PathVariable Long id, @RequestBody Project updateProject) {
        Project result = projectService.updateProject(id, updateProject);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployer(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
