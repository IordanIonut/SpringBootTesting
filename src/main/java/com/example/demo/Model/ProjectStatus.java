package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Data
@Table(name="project_status")
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_project_status;
    @Column(name = "status")
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Project id_project;

    public ProjectStatus() {

    }
}
