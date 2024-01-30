package com.example.demo.Service;

import com.example.demo.Model.Team;
import com.example.demo.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    public TeamRepository teamRepository;
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }
    public List<Team> getTeam(){
        return teamRepository.findAll();
    }
    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }
    public Team updateTeam(Long id, Team team){
        if(teamRepository.existsById(id)){
            team.setId_team(id);
            return teamRepository.save(team);
        }else return null;
    }
    public void deleteTeam(Long id){
        teamRepository.deleteById(id);
    }
}
