package com.proyecto.api.services;

import com.proyecto.api.exception.ErrorEmployeeAssignedProjects;
import com.proyecto.api.exception.InvalidDataException;
import com.proyecto.api.exception.ResourceNotFoundException;

import com.proyecto.api.exception.SaveException;
import com.proyecto.api.models.Employee;
import com.proyecto.api.models.EmployeeProjectId;
import com.proyecto.api.models.Project;
import com.proyecto.api.repositories.EmployeeProjectRepository;
import com.proyecto.api.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeProjectService employeeProjectService;

    public List<Project> getAllActiveProjects() {
        List<Project> projects = projectRepository.findByTerminationDateIsNull();
        if (projects.isEmpty()) {
            throw new ResourceNotFoundException("Projects not found");
        }
        return projects;
    }

    public List<Project> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        if(projects.isEmpty()){
            throw new ResourceNotFoundException("Projects not found");
        }
        return projects;
    }

    public Project getProjectById(Integer id){
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found for this id : " + id));
    }

    public Project saveProject(Project newProject){
        if(newProject==null){
            throw new InvalidDataException("Project cannot be null");
        }
        try {
            return projectRepository.save(newProject);
        } catch (Exception e) {
            throw new SaveException("Error saving project: " + e.getMessage());
        }
    }

    public Project updateDataProject(Integer id, Project project){
        Project existingProject=getProjectById(id);
        if(existingProject !=null){
            existingProject.setDescription(project.getDescription());
            existingProject.setStartDate(project.getStartDate());
            existingProject.setEndDate(project.getEndDate());
            existingProject.setLocation(project.getLocation());
            existingProject.setObservations(project.getObservations());

            try {
                return projectRepository.save(existingProject);
            } catch (Exception e) {
                throw new SaveException("Error updating project: " + e.getMessage());
            }
        }else{
            throw new ResourceNotFoundException("Project with ID " + id + " not found");
        }
    }

    public Project terminateProjectById(Integer id){
        try {
            Project existingProject=getProjectById(id);
            //Comprueba si el proyecto tiene empleados
            if (employeeProjectService.isProjectAssignedToAnyEmployee(id)) {
                throw new Exception("Cannot delete project with assigned employees.");
            }
            // Establece la fecha de terminación
            existingProject.setTerminationDate(LocalDate.now());
            // Guarda los cambios
            return projectRepository.save(existingProject);
        } catch (ErrorEmployeeAssignedProjects e) {
            throw e; // Propaga la excepción de empleado con proyectos asignados
        } catch (Exception e) {
            throw new SaveException("Error updating employee: " + e.getMessage());
        }
    }
}
