package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Data
@Builder
@Setter
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_team;
    @Column(name = "name")
    private String name_team;
    @ManyToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Project id_project;

    public Team() {

    }
}
