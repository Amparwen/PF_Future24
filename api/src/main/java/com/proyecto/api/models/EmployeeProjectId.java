package com.proyecto.api.models;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


/**
 *  Define la clave primaria compuesta con los atributos employeeId y projectId.
 */
@Embeddable
public class EmployeeProjectId implements Serializable {

    private Integer employeeId;
    private Integer projectId;

    /**
     * Constructor por defecto.
     */
    public EmployeeProjectId() {
    }
    /**
     * Constructor con parámetros.
     *
     * @param employeeId el ID del empleado.
     * @param projectId  el ID del proyecto.
     */
    public EmployeeProjectId(Integer employeeId, Integer projectId) {
        this.employeeId = employeeId;
        this.projectId = projectId;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del empleado.
     *
     * @return el ID del empleado.
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * Establece el ID del empleado.
     *
     * @param employeeId el nuevo ID del empleado.
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Obtiene el ID del proyecto.
     *
     * @return el ID del proyecto.
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * Establece el ID del proyecto.
     *
     * @param projectId el nuevo ID del proyecto.
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProjectId that)) return false;
        return Objects.equals(getEmployeeId(), that.getEmployeeId()) && Objects.equals(getProjectId(), that.getProjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getProjectId());
    }
}
