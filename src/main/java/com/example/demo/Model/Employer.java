package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "employer")
public class Employer {
    @Id
    @Column(name = "id_employer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_employee;
    @Column(name = "name")
    @NotBlank(message = "The name is mandatory!")
    @Size(min = 6, message = "The name needs to have minimum 6 characters!")
    private String employee_name;
    @NotBlank(message = "Email address is mandatory!")
    @Email(message = "The email address is invalid!")
    @Column(name = "email", unique = true)
    private String employee_email;
    @Column(name = "phone")
    private Integer employee_phone;
    public Employer() {

    }
}
