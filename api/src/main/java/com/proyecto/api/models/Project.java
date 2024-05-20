package com.proyecto.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="PR_PROYECTOS")
public class Project {
    @Id
    @Column(name = "ID_PROYECTO", nullable = false)
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer idProject;

    @Column(name = "TX_DESCRIPCION", nullable = false, length = 125)
    private String description;

    @Column(name = "F_INICIO", nullable = false)
    private LocalDate startDate;

    @Column(name = "F_FIN")
    private LocalDate endDate;

    @Column(name = "F_BAJA")
    private LocalDate terminationDate;

    @Column(name = "TX_LUGAR", length = 30)
    private String location;

    @Column(name = "TX_OBSERVACIONES", length = 300)
    private String observations;

    /*@ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();*/

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<EmployeeProject> employeeProjects = new HashSet<>();

    /**CONSTRUCTOR POR DEFECTO**/
    public Project() {
    }
    /**CONSTRUCTOR CON PARAMETROS**/
    public Project(String description, LocalDate startDate, LocalDate endDate, LocalDate terminationDate, String location, String observations) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terminationDate = terminationDate;
        this.location = location;
        this.observations = observations;
    }

    /**GETTERS AND SETTERS**/
    public Integer getIdproject() {
        return idProject;
    }

    public void setIdproject(Integer Idproject) {
        this.idProject = Idproject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }

    public void setEmployeeProjects(Set<EmployeeProject> employeeProjects) {
        this.employeeProjects = employeeProjects;
    }

    /**TO STRING**/
    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + idProject +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", terminationDate=" + terminationDate +
                ", location='" + location + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
