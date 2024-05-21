package com.proyecto.api.models;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * EmployeeProject representa la relación entre empleados y proyectos en la base de datos.
 */
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


    /**
     * Constructor por defecto.
     */
    public EmployeeProject() {
    }
    /**
     * Constructor con parámetros.
     *
     * @param id        el identificador compuesto de la relación EmployeeProject.
     * @param startDate la fecha de inicio de la asignación del empleado al proyecto.
     */
    public EmployeeProject(EmployeeProjectId id, LocalDate startDate) {
        this.id = id;
        this.startDate = startDate;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador compuesto de la relación EmployeeProject.
     * @return el identificador compuesto de la relación EmployeeProject.
     */
    public EmployeeProjectId getId() {
        return id;
    }


    /**
     * Establece el identificador compuesto de la relación EmployeeProject.
     * @param id el nuevo identificador compuesto de la relación EmployeeProject.
     */
    public void setId(EmployeeProjectId id) {
        this.id = id;
    }


    /**
     * Obtiene el empleado de la relación EmployeeProject.
     * @return el empleado de la relación EmployeeProject.
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Establece el empleado de la relación EmployeeProject.
     * @param employee el nuevo empleado de la relación EmployeeProject.
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Obtiene el proyecto de la relación EmployeeProject.
     * @return el proyecto de la relación EmployeeProject.
     */
    public Project getProject() {
        return project;
    }
    /**
     * Establece el proyecto de la relación EmployeeProject.
     * @param project el nuevo proyecto de la relación EmployeeProject.
     */
    public void setProject(Project project) {
        this.project = project;
    }
    /**
     * Obtiene la fecha de inicio de la asignación del empleado al proyecto.
     * @return la fecha de inicio de la asignación del empleado al proyecto.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Establece la fecha de inicio de la asignación del empleado al proyecto.
     * @param startDate la nueva fecha de inicio de la asignación del empleado al proyecto.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
