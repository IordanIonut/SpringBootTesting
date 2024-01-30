package com.example.demo.Service;

import com.example.demo.Model.Project;
import com.example.demo.Model.ProjectStatus;
import com.example.demo.Repository.ProjectStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectStatusService {
    @Autowired
    public ProjectStatusRepository projectStatusRepository;
    public ProjectStatus saveProjectStatus(ProjectStatus projectStatus){
        return projectStatusRepository.save(projectStatus);
    }
    public List<ProjectStatus> getProjectStatus(){
        return projectStatusRepository.findAll();
    }
    public Optional<ProjectStatus> getProjectStatusById(Long id){
        return projectStatusRepository.findById(id);
    }
    public ProjectStatus updateProjectStatus(Long id, ProjectStatus projectStatus){
        if(projectStatusRepository.existsById(id)){
            projectStatus.setId_project_status(id);
            return projectStatusRepository.save(projectStatus);
        }
        else return null;
    }
    public void deleteProjectStatus(Long id){
        projectStatusRepository.deleteById(id);
    }
}
