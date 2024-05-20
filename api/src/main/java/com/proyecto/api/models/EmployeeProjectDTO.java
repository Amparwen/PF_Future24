package com.proyecto.api.models;

import java.util.Arrays;
import java.util.List;

public class EmployeeProjectDTO {
    private Integer idProject;
    private List<Integer> idsEmployeeRemove;
    private List<Integer> idsEmployeeAdd;

    public EmployeeProjectDTO() {
    }

    public EmployeeProjectDTO(Integer idProject, List<Integer> idsEmployeeRemove, List<Integer> idsEmployeeAdd) {
        this.idProject = idProject;
        this.idsEmployeeRemove = idsEmployeeRemove;
        this.idsEmployeeAdd = idsEmployeeAdd;
    }

    // Getters y setters

    public Integer getIdProject() {
        return idProject;
    }

    public void setIdProject(Integer idProject) {
        this.idProject = idProject;
    }

    public List<Integer> getIdsEmployeeRemove() {
        return idsEmployeeRemove;
    }

    public void setIdsEmployeeRemove(List<Integer> idsEmployeeRemove) {
        this.idsEmployeeRemove = idsEmployeeRemove;
    }

    public List<Integer> getIdsEmployeeAdd() {
        return idsEmployeeAdd;
    }

    public void setIdsEmployeeAdd(List<Integer> idsEmployeeAdd) {
        this.idsEmployeeAdd = idsEmployeeAdd;
    }

}
