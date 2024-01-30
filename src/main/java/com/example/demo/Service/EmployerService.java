package com.example.demo.Service;

import com.example.demo.Model.Employer;
import com.example.demo.Repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;
    public Employer saveEmployer(Employer employer) {
        return employerRepository.save(employer);
    }
    public List<Employer> getEmployers(){
        return employerRepository.findAll();
    }
    public Optional<Employer> getEmployerById(Long id){
        return employerRepository.findById(id);
    }
    public Employer updateEmployer(Long id, Employer employer){
        if(employerRepository.existsById(id)){
            employer.setId_employee(id);
            return employerRepository.save(employer);
        }
        else return null;
    }
    public void deleteEmployer(Long id){
        employerRepository.deleteById(id);
    }
}
