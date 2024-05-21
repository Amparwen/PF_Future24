package com.proyecto.api.controller;

import com.proyecto.api.exception.ResourceNotFoundException;
import com.proyecto.api.exception.SaveException;
import com.proyecto.api.models.*;
import com.proyecto.api.services.EmployeeProjectService;
import com.proyecto.api.services.EmployeeService;
import com.proyecto.api.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/assignment")
/**
 * Controlador para la asignación de empleados a proyectos
 */
public class EmployeeProjectController {
    @Autowired
    private EmployeeProjectService employeeProjectService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    /**
     * Recupera todas las asignaciones
     * @return una lista con los ids de los proyectos y los ids de los empleados que tienen asignados.
     */
    @GetMapping
    public List<EmployeeProject> getAllEmployeeProjects() {
        return employeeProjectService.getAllEmployeeProjects();
    }

    /**
     * Recupera los ids de los empleados que están asociados a ese proyecto
     * @param projectId id del proyecto
     * @return lista de ids de empleados asociados con ese proyecto
     */
    @GetMapping("{projectId}")
    public List<Integer> getEmployeeIdsByProjectId(@PathVariable Integer projectId) {
        return employeeProjectService.getEmployeeIdsByProjectId(projectId);
    }

    /**
     * Crear una nueva asignación
     * @param employeeProject objeto con el id del proyecto y el id del empleado
     * @return
     */
    @PostMapping("/new")
    public EmployeeProject createEmployeeProject(@RequestBody EmployeeProject employeeProject) {

            Integer idEmployee= employeeProject.getId().getEmployeeId();
            Integer idProject = employeeProject.getId().getProjectId();
            return employeeProjectService.addEmployeeToProject(idEmployee,idProject);


    }

    /**
     *  Actualiza los proyectos de los empleados según la información proporcionada en el objeto EmployeeProjectDTO.
     *  Elimina y agrega empleados a un proyecto específico.
     * @param projectUpdate projectUpdate el DTO que contiene la información del proyecto y las listas de empleados a eliminar y agregar.
     * @return un ResponseEntity con un mensaje de éxito y el estado HTTP OK.
     */
    @PostMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updatedEmployeeProject(@RequestBody EmployeeProjectDTO projectUpdate) {

        Integer projectId = projectUpdate.getIdProject();
        List<Integer> idsEmployeeRemove = projectUpdate.getIdsEmployeeRemove();

        for (Integer employeeId : idsEmployeeRemove) {
            employeeProjectService.deleteEmployeeProject(employeeId, projectId);
        }

        List<Integer> idsEmployeeAdd = projectUpdate.getIdsEmployeeAdd();
        for (Integer employeeId : idsEmployeeAdd) {
            employeeProjectService.addEmployeeToProject(employeeId, projectId);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Employee projects updated successfully");
    }






/*
    @DeleteMapping("/{employeeId}/{projectId}")
    public ResponseEntity<String> deleteEmployeeProject(@PathVariable Integer employeeId, @PathVariable Integer projectId) {
        try {
            EmployeeProjectId employeeProjectId = new EmployeeProjectId(employeeId, projectId);
            if (employeeProjectService.isEmployeeAssignedToAnyProject(employeeId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EmployeeProject not found.");
            }
            employeeProjectService.deleteEmployeeProject(employeeProjectId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }*/
}
