package com.example.demo.Service;

import com.example.demo.Model.Project;
import com.example.demo.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    public ProjectRepository projectRepository;
    public Project saveProject(Project project){
        return projectRepository.save(project);
    }
    public List<Project> getProjects(){
        return projectRepository.findAll();
    }
    public Optional<Project> getProjectById(Long id){
        return projectRepository.findById(id);
    }
    public Project updateProject(Long id, Project project){
        if(projectRepository.existsById(id)){
            project.setId_project(id);
            return projectRepository.save(project);
        }
        else return null;
    }
    public void deleteProject(Long id){
        projectRepository.deleteById(id);
    }
}
