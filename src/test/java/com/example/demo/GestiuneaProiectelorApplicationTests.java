package com.example.demo;

import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GestiuneaProiectelorApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployerRepository employeeRepository;
    @Autowired
    private ProjectRepository projectorRepository;
    @Autowired
    private ProjectStatusRepository projectStatusRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private static List<Employer> employees;
    @Autowired
    private static List<Project> projects;
    @Autowired
    private static List<ProjectStatus> projectStatuses;
    @Autowired
    private static List<Team> teams;
    @Autowired
    private static List<TeamMember> teamMembers;

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper.writeValueAsString(obj);
    }

    @BeforeAll
    void setup() {
        employeeRepository.deleteAll();
        projectorRepository.deleteAll();
        projectStatusRepository.deleteAll();
        teamRepository.deleteAll();

        Employer employee = Employer.builder().employee_name("Fadatare").employee_email("ramesh@gmail.com").employee_phone(123123123).build();
        Employer employer2 = Employer.builder().employee_name("Fadatar1").employee_email("rames1@gmail.com").employee_phone(123123123).build();
        Employer employer3 = Employer.builder().employee_name("Fadatar1").employee_email("ramesh2@gmail.com").employee_phone(123123123).build();
        Project project1 = Project.builder().project_name("asdada").team_size(5).time_start(LocalDateTime.parse("2024-12-31T23:59:59")).time_end(LocalDateTime.parse("2024-12-31T23:59:59")).id_employer(employee).build();
        Project project2 = Project.builder().project_name("asdada").team_size(5).time_start(LocalDateTime.parse("2024-12-31T23:59:59")).time_end(LocalDateTime.parse("2024-12-31T23:59:59")).id_employer(employer2).build();
        Project project3 = Project.builder().project_name("asdada").team_size(5).time_start(LocalDateTime.parse("2024-12-31T23:59:59")).time_end(LocalDateTime.parse("2024-12-31T23:59:59")).id_employer(employer3).build();
        ProjectStatus projectStatus1 = ProjectStatus.builder().status(true).id_project(project1).build();
        ProjectStatus projectStatus2 = ProjectStatus.builder().status(false).id_project(project2).build();
        ProjectStatus projectStatus3 = ProjectStatus.builder().status(true).id_project(project3).build();
        Team team1 = Team.builder().name_team("asdaa").id_project(project1).build();
        Team team2 = Team.builder().name_team("asdaa123").id_project(project2).build();
        Team team3 = Team.builder().name_team("asdaa123213").id_project(project3).build();
        TeamMember teamMember1 = TeamMember.builder().id_team(team1).id_employer(employee).build();
        TeamMember teamMember2 = TeamMember.builder().id_team(team2).id_employer(employer2).build();
        TeamMember teamMember3 = TeamMember.builder().id_team(team3).id_employer(employer3).build();

        employeeRepository.save(employee);
        employeeRepository.save(employer2);
        employeeRepository.save(employer3);
        projectorRepository.save(project1);
        projectorRepository.save(project2);
        projectorRepository.save(project3);
        projectStatusRepository.save(projectStatus1);
        projectStatusRepository.save(projectStatus2);
        projectStatusRepository.save(projectStatus3);
        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamMemberRepository.save(teamMember1);
        teamMemberRepository.save(teamMember2);
        teamMemberRepository.save(teamMember3);

        employees = Arrays.asList(employee, employer2);
        projects = Arrays.asList(project1, project2);
        projectStatuses = Arrays.asList(projectStatus1, projectStatus2);
        teams = Arrays.asList(team1, team2);
        teamMembers = Arrays.asList(teamMember1, teamMember2);
    }

    @Test
    public void employeeSave() throws Exception {
        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employees.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_employee", is(employees.get(0).getId_employee().intValue())))
                .andExpect(jsonPath("$.employee_name", is(employees.get(0).getEmployee_name())))
                .andExpect(jsonPath("$.employee_phone", is(employees.get(0).getEmployee_phone())))
                .andExpect(jsonPath("$.employee_email", is(employees.get(0).getEmployee_email())));
    }

    @Test
    public void employeeSaveDuplicateEmail() throws Exception {
        Employer employeeWithDuplicateEmail = Employer.builder().employee_name("John Doe").employee_email("ramesh@gmail.com").employee_phone(1234567890).build();
        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeWithDuplicateEmail)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void employeeGetAll() throws Exception {
        mockMvc.perform(get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employees)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id_employee", is(employees.get(0).getId_employee().intValue())))
                .andExpect(jsonPath("$[0].employee_name", is(employees.get(0).getEmployee_name())))
                .andExpect(jsonPath("$[0].employee_phone", is(employees.get(0).getEmployee_phone())))
                .andExpect(jsonPath("$[0].employee_email", is(employees.get(0).getEmployee_email())))
                .andExpect(jsonPath("$[1].id_employee", is(employees.get(1).getId_employee().intValue())))
                .andExpect(jsonPath("$[1].employee_name", is(employees.get(1).getEmployee_name())))
                .andExpect(jsonPath("$[1].employee_phone", is(employees.get(1).getEmployee_phone())))
                .andExpect(jsonPath("$[1].employee_email", is(employees.get(1).getEmployee_email())));
    }

    @Test
    public void employeeGetById() throws Exception {
        mockMvc.perform(get("/api/employee/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_employee", is(employees.get(0).getId_employee().intValue())))
                .andExpect(jsonPath("$[0].employee_name", is(employees.get(0).getEmployee_name())))
                .andExpect(jsonPath("$[0].employee_phone", is(employees.get(0).getEmployee_phone())))
                .andExpect(jsonPath("$[0].employee_email", is(employees.get(0).getEmployee_email())));

    }

    @Test
    public void updateEmployee() throws Exception {
        Long employeeIdToUpdate = 2L;
        Employer updatedEmployee = Employer.builder().employee_name("firstName2").employee_email("sadsad@yahoo.com").employee_phone(2312313).build();
        mockMvc.perform(put("/api/employee/{id}", employeeIdToUpdate)
                        .content(asJsonString(updatedEmployee))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employee_name").value("firstName2"))
                .andExpect(jsonPath("$.employee_email").value("sadsad@yahoo.com"))
                .andExpect(jsonPath("$.employee_phone").value(2312313));
    }

    @Test
    public void deleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employee/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void projectSave() throws Exception {
        mockMvc.perform(post("/api/project/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projects.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_project", is(projects.get(0).getId_project().intValue())))
                .andExpect(jsonPath("$.project_name", is(projects.get(0).getProject_name())))
                .andExpect(jsonPath("$.team_size", is(projects.get(0).getTeam_size())))
                .andExpect(jsonPath("$.time_start", is(projects.get(0).getTime_start().toString())))
                .andExpect(jsonPath("$.time_end", is(projects.get(0).getTime_end().toString())))
                .andExpect(jsonPath("$.id_employer.id_employee", is(projects.get(0).getId_employer().getId_employee().intValue())));
    }

    @Test
    public void projectGetAll() throws Exception {
        mockMvc.perform(get("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projects)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id_project", is(projects.get(0).getId_project().intValue())))
                .andExpect(jsonPath("$[0].project_name", is(projects.get(0).getProject_name())))
                .andExpect(jsonPath("$[0].team_size", is(projects.get(0).getTeam_size())))
                .andExpect(jsonPath("$[0].time_start", is(projects.get(0).getTime_start().toString())))
                .andExpect(jsonPath("$[0].time_end", is(projects.get(0).getTime_end().toString())))
                .andExpect(jsonPath("$[0].id_employer.id_employee", is(projects.get(0).getId_employer().getId_employee().intValue())))
                .andExpect(jsonPath("$[1].id_project", is(projects.get(1).getId_project().intValue())))
                .andExpect(jsonPath("$[1].project_name", is(projects.get(1).getProject_name())))
                .andExpect(jsonPath("$[1].team_size", is(projects.get(1).getTeam_size())))
                .andExpect(jsonPath("$[1].time_start", is(projects.get(1).getTime_start().toString())))
                .andExpect(jsonPath("$[1].time_end", is(projects.get(1).getTime_end().toString())))
                .andExpect(jsonPath("$[1].id_employer.id_employee", is(projects.get(1).getId_employer().getId_employee().intValue())));
    }

    @Test
    public void projectGetById() throws Exception {
        mockMvc.perform(get("/api/project/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_project", is(projects.get(0).getId_project().intValue())))
                .andExpect(jsonPath("$[0].project_name", is(projects.get(0).getProject_name())))
                .andExpect(jsonPath("$[0].team_size", is(projects.get(0).getTeam_size())))
                .andExpect(jsonPath("$[0].time_start", is(projects.get(0).getTime_start().toString())))
                .andExpect(jsonPath("$[0].time_end", is(projects.get(0).getTime_end().toString())))
                .andExpect(jsonPath("$[0].id_employer.id_employee", is(projects.get(0).getId_employer().getId_employee().intValue())));

    }

    @Test
    public void updateProject() throws Exception {
        Long projectId = 2L;
        Project updatedProject = Project.builder().project_name("asdada").team_size(5).time_end(LocalDateTime.parse("2024-12-31T23:59:59")).time_start(LocalDateTime.parse("2024-12-31T23:59:59")).id_employer(employees.get(1)).build();
        mockMvc.perform(put("/api/project/{id}", projectId)
                        .content(asJsonString(updatedProject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project_name").value(updatedProject.getProject_name()))
                .andExpect(jsonPath("$.team_size").value(updatedProject.getTeam_size()))
                .andExpect(jsonPath("$.time_end").value(updatedProject.getTime_end().toString()))
                .andExpect(jsonPath("$.time_start").value(updatedProject.getTime_start().toString()))
                .andExpect(jsonPath("$.id_employer.id_employee").value(updatedProject.getId_employer().getId_employee().intValue()));
    }

    @Test
    public void deleteProject() throws Exception {
        mockMvc.perform(delete("/api/project/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void projectStatusSave() throws Exception {
        mockMvc.perform(post("/api/project/status/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectStatuses.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_project_status", is(projectStatuses.get(0).getId_project_status().intValue())))
                .andExpect(jsonPath("$.status", is(projectStatuses.get(0).getStatus())))
                .andExpect(jsonPath("$.id_project.id_project", is(projectStatuses.get(0).getId_project().getId_project().intValue())));
    }

    @Test
    public void projectStatusGetAll() throws Exception {
        mockMvc.perform(get("/api/project/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectStatuses)))
                .andDo(print()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void projectStatusGetById() throws Exception {
        mockMvc.perform(get("/api/project/status/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_project_status", is(projectStatuses.get(0).getId_project_status().intValue())))
                .andExpect(jsonPath("$[0].status", is(projectStatuses.get(0).getStatus())))
                .andExpect(jsonPath("$[0].id_project.id_project", is(projectStatuses.get(0).getId_project().getId_project().intValue())));
    }

    @Test
    public void projectStatusUpdate() throws Exception {
        Long projectStatusId = 2L;
        ProjectStatus projectStatus = ProjectStatus.builder().status(true).id_project(projects.get(1)).build();
        mockMvc.perform((put("/api/project/status/{id}", projectStatusId))
                        .content(asJsonString(projectStatus))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_project_status").value(2L))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.id_project.id_project").value(projectStatuses.get(1).getId_project().getId_project().intValue()));
    }

    @Test
    public void projectStatusDelete() throws Exception {
        mockMvc.perform(delete("/api/project/status/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void teamSave() throws Exception {
        mockMvc.perform(post("/api/team/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teams.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_team", is(teams.get(0).getId_team().intValue())))
                .andExpect(jsonPath("$.name_team", is(teams.get(0).getName_team())))
                .andExpect(jsonPath("$.id_project.id_project", is(teams.get(0).getId_project().getId_project().intValue())));
    }

    @Test
    public void teamGetAll() throws Exception {
        mockMvc.perform(get("/api/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teams)))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void teamGetById() throws Exception {
        mockMvc.perform(get("/api/team/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id_team", is(teams.get(0).getId_team().intValue())))
                .andExpect(jsonPath("$[0].name_team", is(teams.get(0).getName_team())))
                .andExpect(jsonPath("$[0].id_project.id_project", is(teams.get(0).getId_project().getId_project().intValue())));
    }

    @Test
    public void teamUpdate() throws Exception {
        Long teamId = 2L;
        Team teamUpdate = Team.builder().name_team("asdsaad").id_project(projects.get(0)).build();
        mockMvc.perform(put("/api/team/{id}", teamId)
                        .content(asJsonString(teamUpdate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_team", is(teams.get(1).getId_team().intValue())))
                .andExpect(jsonPath("$.name_team", is(teamUpdate.getName_team())))
                .andExpect(jsonPath("$.id_project.id_project", is(teamUpdate.getId_project().getId_project().intValue())));
    }

    @Test
    public void teamDelete() throws Exception {
        mockMvc.perform(delete("/api/team/{id}", 4))
                .andExpect(status().isAccepted());
    }

    @Test
    public void teamMemberSave() throws Exception {
        mockMvc.perform(post("/api/team/member/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamMembers.get(0))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_team_member", is(teamMembers.get(0).getId_team_member().intValue())))
                .andExpect(jsonPath("$.id_team.id_team", is(teamMembers.get(0).getId_team().getId_team().intValue())))
                .andExpect(jsonPath("$.id_employer.id_employee", is(teamMembers.get(0).getId_employer().getId_employee().intValue())));
    }

    @Test
    public void teamMemberGetAll() throws Exception {
        mockMvc.perform(get("/api/team/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamMembers)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
        ;
    }

    @Test
    public void teamMemberGetById() throws Exception {
        mockMvc.perform(get("/api/team/member/id?id=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id_team_member", is(teamMembers.get(0).getId_team_member().intValue())))
                .andExpect(jsonPath("$[0].id_team.id_team", is(teamMembers.get(0).getId_team().getId_team().intValue())))
                .andExpect(jsonPath("$[0].id_employer.id_employee", is(teamMembers.get(0).getId_employer().getId_employee().intValue())));
    }

    @Test
    public void teamMemberUpdate() throws Exception{
        Long teamMemberId = 2l;
        TeamMember teamMemberUpdate = TeamMember.builder().id_team(teams.get(1)).id_employer(employees.get(1)).build();
        mockMvc.perform(put("/api/team/member/{id}", teamMemberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(teamMemberUpdate))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void teamMemberDelete() throws Exception{
        mockMvc.perform(delete("/api/team/member/{id}", 4)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
