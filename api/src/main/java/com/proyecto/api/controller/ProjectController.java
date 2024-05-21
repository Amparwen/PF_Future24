package com.proyecto.api.controller;

import com.proyecto.api.models.Project;
import com.proyecto.api.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
/**
 * Controlador para la gestión de proyectos
 */
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * Recupera todos los proyectos activos.
     *
     * @return una lista de proyectos activos.
     */
    @GetMapping("/active")
    public List<Project> getAllActiveProjects() {
        return projectService.getAllActiveProjects();
    }

    /**
     * Recupera todos los proyectos.
     *
     * @return una lista de todos los proyectos.
     */
    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    /**
     * Recupera un proyecto por su ID.
     *
     * @param id el ID del proyecto.
     * @return un ResponseEntity que contiene el proyecto y el estado HTTP OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@Valid @PathVariable int id) {
        Project project =projectService.getProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     * Crea un nuevo proyecto.
     *
     * @param project el proyecto a crear.
     * @return un ResponseEntity que contiene el proyecto guardado y el estado HTTP CREATED.
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
    /**
     * Actualiza los datos de un proyecto existente.
     *
     * @param id el ID del proyecto a actualizar.
     * @param project el proyecto con los nuevos datos.
     * @return un ResponseEntity que contiene el proyecto editado y el estado HTTP OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project>  updateDataProject(@PathVariable int id, @Valid @RequestBody Project project) {
        Project editProject = projectService.updateDataProject(id, project);
        return new ResponseEntity<>(editProject, HttpStatus.OK);

    }
    /**
     * Termina un proyecto por su ID.
     *
     * @param id el ID del proyecto a terminar.
     * @return un ResponseEntity que contiene el proyecto terminado y el estado HTTP OK.
     */
    @PutMapping("/terminate/{id}")
    public ResponseEntity<Project> terminateProjectById(@Valid @PathVariable int id) {
        Project terminatedProject = projectService.terminateProjectById(id);
        return ResponseEntity.ok(terminatedProject);
    }
}
