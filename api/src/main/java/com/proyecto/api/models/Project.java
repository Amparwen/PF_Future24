package com.proyecto.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
/**
 * Project representa la entidad de un proyecto en la base de datos.
 */
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

    /*
     * Relación bidireccional con EmployeeProject.
     * mappedBy une las tablas Project y EmployeeProject a través de la propiedad project.
     * cascade = CascadeType.ALL indica que todas las operaciones de persistencia (persist, merge, remove, refresh, detach)
     * realizadas en la entidad Project se deben propagar a las entidades EmployeeProject.
     * orphanRemoval = true indica que las instancias de EmployeeProject que están asociadas con un Project deben ser
     * eliminadas si se eliminan de la colección employeeProjects.
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<EmployeeProject> employeeProjects = new HashSet<>();

    /**
     * Constructor por defecto.
     */
    public Project() {
    }
    /**
     * Constructor con parámetros.
     *
     * @param description    la descripción del proyecto.
     * @param startDate      la fecha de inicio del proyecto.
     * @param endDate        la fecha de fin del proyecto.
     * @param terminationDate la fecha de baja del proyecto.
     * @param location       la ubicación del proyecto.
     * @param observations   las observaciones sobre el proyecto.
     */
    public Project(String description, LocalDate startDate, LocalDate endDate, LocalDate terminationDate, String location, String observations) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.terminationDate = terminationDate;
        this.location = location;
        this.observations = observations;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del proyecto.
     * @return el ID del proyecto.
     */
    public Integer getIdProject() {
        return idProject;
    }


    /**
     * Establece el ID del proyecto.
     * @param idProject el nuevo ID del proyecto.
     */
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }


    /**
     * Obtiene la descripción del proyecto.
     * @return la descripción del proyecto.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Establece la descripción del proyecto.
     * @param description la nueva descripción del proyecto.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Obtiene la fecha de inicio del proyecto.
     * @return la fecha de inicio del proyecto.
     */
    public LocalDate getStartDate() {
        return startDate;
    }


    /**
     * Establece la fecha de inicio del proyecto.
     * @param startDate la nueva fecha de inicio del proyecto.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    /**
     * Obtiene la fecha de fin del proyecto.
     * @return la fecha de fin del proyecto.
     */
    public LocalDate getEndDate() {
        return endDate;
    }


    /**
     * Establece la fecha de fin del proyecto.
     * @param endDate la nueva fecha de fin del proyecto.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    /**
     * Obtiene la fecha de baja del proyecto.
     * @return la fecha de baja del proyecto.
     */
    public LocalDate getTerminationDate() {
        return terminationDate;
    }


    /**
     * Establece la fecha de baja del proyecto.
     * @param terminationDate la nueva fecha de baja del proyecto.
     */
    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }


    /**
     * Obtiene la ubicación del proyecto.
     * @return la ubicación del proyecto.
     */
    public String getLocation() {
        return location;
    }


    /**
     * Establece la ubicación del proyecto.
     * @param location la nueva ubicación del proyecto.
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     * Obtiene las observaciones del proyecto.
     * @return las observaciones del proyecto.
     */
    public String getObservations() {
        return observations;
    }


    /**
     * Establece las observaciones del proyecto.
     * @param observations las nuevas observaciones del proyecto.
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }


    /**
     * Obtiene la colección de EmployeeProject asociada al proyecto.
     * @return la colección de EmployeeProject.
     */
    public Set<EmployeeProject> getEmployeeProjects() {
        return employeeProjects;
    }


    /**
     * Establece la colección de EmployeeProject asociada al proyecto.
     * @param employeeProjects la nueva colección de EmployeeProject.
     */
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
