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
@Service
public class EmployeeProjectService {
    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<EmployeeProject> getAllEmployeeProjects() {
        return employeeProjectRepository.findAll();
    }

    public EmployeeProject addEmployeeToProject(Integer idEmployee, Integer idProject) {
        // Verificamos que el empleado y el proyecto existan
        Employee employee = employeeRepository.findActiveEmployeeById(idEmployee);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not exist");
        }
        Project project = projectRepository.findActiveProjectById(idProject);
        if (project == null) {
            throw new ResourceNotFoundException("Project not exist");
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

    public void deleteEmployeeProject(Integer idEmployee, Integer idProject) {
        // Verificamos que el empleado y el proyecto existan
        Employee employee = employeeRepository.findActiveEmployeeById(idEmployee);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not exist");
        }
        Project project = projectRepository.findActiveProjectById(idProject);
        if (project == null) {
            throw new ResourceNotFoundException("Project not exist");
        }
        EmployeeProjectId id = new EmployeeProjectId(idEmployee, idProject);
        employeeProjectRepository.deleteById(id);
    }
    public boolean isEmployeeAssignedToAnyProject(Integer employeeId) {
        return employeeProjectRepository.existsByEmployeeId(employeeId);
    }
    public boolean isProjectAssignedToAnyEmployee(Integer projectId) {
        return employeeProjectRepository.existsByProjectId(projectId);
    }

    public List<Integer> getEmployeeIdsByProjectId(Integer projectId) {
        return employeeProjectRepository.findEmployeeIdsByProjectId(projectId);
    }
}
