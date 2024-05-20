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
 * Constructores
 */
    public EmployeeProjectId() {
    }

    public EmployeeProjectId(Integer employeeId, Integer projectId) {
        this.employeeId = employeeId;
        this.projectId = projectId;
    }

    /**
     * GETTERS AND SETTERS
     */

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getProjectId() {
        return projectId;
    }

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
