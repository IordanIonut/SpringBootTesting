package com.example.demo.Controller;

import com.example.demo.Model.TeamMember;
import com.example.demo.Service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/team/member")
public class TeamMemberController {
    @Autowired
    public TeamMemberService teamMemberService;
    @PostMapping("/add")
    public ResponseEntity<TeamMember> saveTeamMember(@RequestBody TeamMember teamMember) {
        TeamMember save = teamMemberService.saveTeam(teamMember);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<TeamMember>> getAllTeamMembers(){
        List<TeamMember> teamMembers = teamMemberService.getTeam();
        return ResponseEntity.ok(teamMembers);
    }
    @GetMapping("/id")
    public ResponseEntity<List<TeamMember>> getTeamMemberById(@RequestParam Long id){
        Optional<TeamMember> teamMember = teamMemberService.getTeamById(id);
        return teamMember.map(e -> ResponseEntity.ok(Collections.singletonList(e)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable Long id, @RequestBody TeamMember updatedTeamMember) {
        TeamMember result = teamMemberService.updateTeam(id, updatedTeamMember);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTeamMember(@PathVariable Long id) {
        teamMemberService.deleteTeam(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
}
