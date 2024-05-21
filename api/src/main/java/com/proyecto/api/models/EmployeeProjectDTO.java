package com.proyecto.api.models;

import java.util.Arrays;
import java.util.List;
/**
 * EmployeeProjectDTO es un objeto de transferencia de datos que encapsula la información
 * necesaria para actualizar los empleados asignados a un proyecto.
 */
public class EmployeeProjectDTO {
    private Integer idProject;
    private List<Integer> idsEmployeeRemove;
    private List<Integer> idsEmployeeAdd;


    /**
     * Constructor por defecto.
     */
    public EmployeeProjectDTO() {
    }
    /**
     * Constructor con parámetros.
     *
     * @param idProject        el ID del proyecto.
     * @param idsEmployeeRemove la lista de IDs de los empleados que se deben eliminar del proyecto.
     * @param idsEmployeeAdd    la lista de IDs de los empleados que se deben agregar al proyecto.
     */
    public EmployeeProjectDTO(Integer idProject, List<Integer> idsEmployeeRemove, List<Integer> idsEmployeeAdd) {
        this.idProject = idProject;
        this.idsEmployeeRemove = idsEmployeeRemove;
        this.idsEmployeeAdd = idsEmployeeAdd;
    }

    // Getters y setters

    /**
     * Obtiene el ID del proyecto.
     * @return el ID del proyecto.
     */
    public Integer getIdProject() {
        return idProject;
    }

    /**
     * Establece el ID del proyecto.
     *
     * @param idProject el nuevo ID del proyecto.
     */
    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    /**
     * Obtiene la lista de IDs de los empleados que se deben eliminar del proyecto.
     *
     * @return la lista de IDs de los empleados que se deben eliminar del proyecto.
     */
    public List<Integer> getIdsEmployeeRemove() {
        return idsEmployeeRemove;
    }

    /**
     * Establece la lista de IDs de los empleados que se deben eliminar del proyecto.
     *
     * @param idsEmployeeRemove la nueva lista de IDs de los empleados que se deben eliminar del proyecto.
     */
    public void setIdsEmployeeRemove(List<Integer> idsEmployeeRemove) {
        this.idsEmployeeRemove = idsEmployeeRemove;
    }

    /**
     * Obtiene la lista de IDs de los empleados que se deben agregar al proyecto.
     *
     * @return la lista de IDs de los empleados que se deben agregar al proyecto.
     */
    public List<Integer> getIdsEmployeeAdd() {
        return idsEmployeeAdd;
    }

    /**
     * Establece la lista de IDs de los empleados que se deben agregar al proyecto.
     *
     * @param idsEmployeeAdd la nueva lista de IDs de los empleados que se deben agregar al proyecto.
     */
    public void setIdsEmployeeAdd(List<Integer> idsEmployeeAdd) {
        this.idsEmployeeAdd = idsEmployeeAdd;
    }

}
