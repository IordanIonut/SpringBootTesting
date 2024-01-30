package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "id_project")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_project;
    @Column(name = "name")
    private String project_name;
    @Column(name = "team_size")
    private Integer team_size;
    @Column(name = "time_end")
    private LocalDateTime time_end;
    @Column(name = "time_start")
    private LocalDateTime time_start;
    @ManyToOne
    @JoinColumn(name = "id_employer", referencedColumnName = "id_employer")
    private Employer id_employer;

    public Project() {

    }
}
