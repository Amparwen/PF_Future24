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
/**
 * ProjectService proporciona métodos para gestionar los proyectos.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeProjectService employeeProjectService;



    /**
     * Obtiene todos los proyectos activos (aquellos sin fecha de terminación).
     * @return una lista de proyectos activos.
     * @throws ResourceNotFoundException si no se encuentran proyectos.
     */
    public List<Project> getAllActiveProjects() {
        List<Project> projects = projectRepository.findByTerminationDateIsNull();
        if (projects.isEmpty()) {
            throw new ResourceNotFoundException("El proyecto no se ha encontrado");
        }
        return projects;
    }


    /**
     * Obtiene todos los proyectos.
     * @return una lista de todos los proyectos.
     * @throws ResourceNotFoundException si no se encuentran proyectos.
     */
    public List<Project> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        if(projects.isEmpty()){
            throw new ResourceNotFoundException("El proyecto no se ha encontrado");
        }
        return projects;
    }


    /**
     * Obtiene un proyecto por su ID.
     * @param id el ID del proyecto.
     * @return el proyecto con el ID especificado.
     * @throws ResourceNotFoundException si no se encuentra el proyecto con el ID especificado.
     */
    public Project getProjectById(Integer id){
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El proyecto con id "+ id +" no se ha encontrado" ));
    }


    /**
     * Guarda un nuevo proyecto.
     * @param newProject el nuevo proyecto a guardar.
     * @return el proyecto guardado.
     * @throws InvalidDataException si el proyecto es nulo.
     * @throws SaveException si ocurre un error al guardar el proyecto.
     */
    public Project saveProject(Project newProject){
        if(newProject==null){
            throw new InvalidDataException("El proyecto no puede ser nulo");
        }
        try {
            return projectRepository.save(newProject);
        } catch (Exception e) {
            throw new SaveException("Error al guardar el proyecto: " + e.getMessage());
        }
    }


    /**
     * Actualiza los datos de un proyecto existente.
     * @param id      el ID del proyecto a actualizar.
     * @param project los nuevos datos del proyecto.
     * @return el proyecto actualizado.
     * @throws ResourceNotFoundException si el proyecto no se encuentra.
     * @throws SaveException si ocurre un error al actualizar el proyecto.
     */
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
                throw new SaveException("Error al actualizar el proyecto: " + e.getMessage());
            }
        }else{
            throw new ResourceNotFoundException("El proyecto con el id: " + id + " no ha sido encontrado");
        }
    }

    /**
     * Termina un proyecto estableciendo su fecha de terminación a la fecha actual.
     * @param id el ID del proyecto a terminar.
     * @return el proyecto terminado.
     * @throws ErrorEmployeeAssignedProjects si el proyecto tiene empleados asignados.
     * @throws SaveException si ocurre un error al actualizar el proyecto.
     */
    public Project terminateProjectById(Integer id){
        try {
            Project existingProject=getProjectById(id);
            //Comprueba si el proyecto tiene empleados
            if (employeeProjectService.isProjectAssignedToAnyEmployee(id)) {
                throw new Exception("No puede eliminar el proyecto si tiene empleados asignados.");
            }
            // Establece la fecha de terminación
            existingProject.setTerminationDate(LocalDate.now());
            // Guarda los cambios
            return projectRepository.save(existingProject);
        } catch (ErrorEmployeeAssignedProjects e) {
            throw e; // Propaga la excepción de empleado con proyectos asignados
        } catch (Exception e) {
            throw new SaveException("Error al actualizar el proyecto: " + e.getMessage());
        }
    }
}
