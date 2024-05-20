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
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/active")
    public List<Project> getAllActiveProjects() {
        return projectService.getAllActiveProjects();
    }


    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@Valid @PathVariable int id) {
        Project project =projectService.getProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project savedProject = projectService.saveProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Project>  updateDataProject(@PathVariable int id, @Valid @RequestBody Project project) {
        Project editProject = projectService.updateDataProject(id, project);
        return new ResponseEntity<>(editProject, HttpStatus.OK);

    }

    @PutMapping("/terminate/{id}")
    public ResponseEntity<Project> terminateProjectById(@Valid @PathVariable int id) {
        Project terminatedProject = projectService.terminateProjectById(id);
        return ResponseEntity.ok(terminatedProject);
    }
}
