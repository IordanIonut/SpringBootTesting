package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Data
@Table(name="team_member")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_team_member;
    @ManyToOne
    @JoinColumn(name = "id_team", referencedColumnName = "id_team")
    private Team id_team;
    @ManyToOne
    @JoinColumn(name = "id_employer", referencedColumnName = "id_employer")
    private Employer id_employer;

    public TeamMember() {

    }
}
