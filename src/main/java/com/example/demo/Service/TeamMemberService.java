package com.example.demo.Service;

import com.example.demo.Model.Team;
import com.example.demo.Model.TeamMember;
import com.example.demo.Repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {
    @Autowired
    public TeamMemberRepository teamMemberRepository;
    public TeamMember saveTeam(TeamMember team) {
        return teamMemberRepository.save(team);
    }
    public List<TeamMember> getTeam(){
        return teamMemberRepository.findAll();
    }
    public Optional<TeamMember> getTeamById(Long id){
        return teamMemberRepository.findById(id);
    }
    public TeamMember updateTeam(Long id, TeamMember team){
        if(teamMemberRepository.existsById(id)){
            team.setId_team_member(id);
            return teamMemberRepository.save(team);
        }else return null;
    }
    public void deleteTeam(Long id){
        teamMemberRepository.deleteById(id);
    }
}
