package com.proyecto.api.services;

import com.proyecto.api.exception.ResourceNotFoundException;
import com.proyecto.api.models.Employee;
import com.proyecto.api.models.EmployeeProject;
import com.proyecto.api.models.EmployeeProjectId;
import com.proyecto.api.models.Project;
import com.proyecto.api.repositories.EmployeeProjectRepository;
import com.proyecto.api.repositories.EmployeeRepository;
import com.proyecto.api.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**
 * EmployeeProjectService proporciona métodos para gestionar las relaciones entre empleados y proyectos.
 */
@Service
public class EmployeeProjectService {
    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Obtiene todas las relaciones entre empleados y proyectos.
     * @return una lista de todas las relaciones EmployeeProject.
     */
    public List<EmployeeProject> getAllEmployeeProjects() {
        return employeeProjectRepository.findAll();
    }

    /**
     * Asigna un empleado a un proyecto.
     *
     * @param idEmployee el ID del empleado.
     * @param idProject  el ID del proyecto.
     * @return el objeto EmployeeProject guardado.
     * @throws ResourceNotFoundException si el empleado o el proyecto no existen.
     */
    public EmployeeProject addEmployeeToProject(Integer idEmployee, Integer idProject) {
        // Verificamos que el empleado y el proyecto existan
        Employee employee = employeeRepository.findActiveEmployeeById(idEmployee);
        if (employee == null) {
            throw new ResourceNotFoundException("El empleado no existe");
        }
        Project project = projectRepository.findActiveProjectById(idProject);
        if (project == null) {
            throw new ResourceNotFoundException("El proyecto no existe");
        }

        //Establecemos la fecha actual como fecha de asignacion del empleado al proyecto
        LocalDate date = LocalDate.now();

        // Creamos el id con los ids de empleados y proyecto
        EmployeeProjectId id = new EmployeeProjectId(idEmployee, idProject);
        // Creamos el objeto
        EmployeeProject employeeProject = new EmployeeProject(id, date);

        // Agregamos el objeto empleado y proyecto
        employeeProject.setEmployee(employee);
        employeeProject.setProject(project);

        return employeeProjectRepository.save(employeeProject);
    }

    /**
     * Elimina la asignación de un empleado a un proyecto.
     *
     * @param idEmployee el ID del empleado.
     * @param idProject  el ID del proyecto.
     * @throws ResourceNotFoundException si el empleado o el proyecto no existen.
     */
    public void deleteEmployeeProject(Integer idEmployee, Integer idProject) {
        // Verificamos que el empleado y el proyecto existan
        Employee employee = employeeRepository.findActiveEmployeeById(idEmployee);
        if (employee == null) {
            throw new ResourceNotFoundException("El empleado no existe");
        }
        Project project = projectRepository.findActiveProjectById(idProject);
        if (project == null) {
            throw new ResourceNotFoundException("El proyecto no existe");
        }
        EmployeeProjectId id = new EmployeeProjectId(idEmployee, idProject);
        employeeProjectRepository.deleteById(id);
    }

    /**
     * Verifica si un empleado está asignado a algún proyecto.
     *
     * @param employeeId el ID del empleado.
     * @return true si el empleado está asignado a algún proyecto, de lo contrario false.
     */
    public boolean isEmployeeAssignedToAnyProject(Integer employeeId) {
        return employeeProjectRepository.existsByEmployeeId(employeeId);
    }

    /**
     * Verifica si un proyecto está asignado a algún empleado.
     *
     * @param projectId el ID del proyecto.
     * @return true si el proyecto está asignado a algún empleado, de lo contrario false.
     */
    public boolean isProjectAssignedToAnyEmployee(Integer projectId) {
        return employeeProjectRepository.existsByProjectId(projectId);
    }

    /**
     * Obtiene los IDs de los empleados asignados a un proyecto específico.
     *
     * @param projectId el ID del proyecto.
     * @return una lista de IDs de empleados asignados al proyecto.
     */
    public List<Integer> getEmployeeIdsByProjectId(Integer projectId) {
        return employeeProjectRepository.findEmployeeIdsByProjectId(projectId);
    }


}
