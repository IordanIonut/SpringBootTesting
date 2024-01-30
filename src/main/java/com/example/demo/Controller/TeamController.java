package com.example.demo.Controller;

import com.example.demo.Model.Team;
import com.example.demo.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    public TeamService teamService;
    @PostMapping("/add")
    public ResponseEntity<Team> saveTeam(@RequestBody Team team){
        Team save = teamService.saveTeam(team);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Team>> getTeams(){
        List<Team> teams = teamService.getTeam();
        return ResponseEntity.ok(teams);
    }
    @GetMapping("/id")
    public ResponseEntity<List<Team>> getTeamById(@RequestParam Long id){
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeamById(@PathVariable Long id, @RequestBody Team updateTeam) {
        Team result = teamService.updateTeam(id, updateTeam);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployer(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
