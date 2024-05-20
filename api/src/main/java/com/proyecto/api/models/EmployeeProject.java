package com.proyecto.api.models;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity(name = "PR_EMPLEADOS_PROYECTO")
public class EmployeeProject {


    @EmbeddedId
    private EmployeeProjectId id;


    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "ID_EMPLEADO")
    private Employee employee;


    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "ID_PROYECTO")
    private Project project;

    @Column(name = "F_ALTA", nullable = false)
    private LocalDate startDate;


    /**CONSTRUCTOR**/
    public EmployeeProject() {
    }

    public EmployeeProject(EmployeeProjectId id, LocalDate startDate) {
        this.id = id;
        this.startDate = startDate;
    }

    /** GETTERS & SETTERS*/
    public EmployeeProjectId getId() {
        return id;
    }

    public void setId(EmployeeProjectId id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate now) {
        this.startDate = startDate;
    }
}
