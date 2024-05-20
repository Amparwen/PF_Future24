package com.proyecto.api.services;

import com.proyecto.api.exception.ErrorEmployeeAssignedProjects;
import com.proyecto.api.exception.InvalidDataException;
import com.proyecto.api.exception.ResourceNotFoundException;
import com.proyecto.api.exception.SaveException;
import com.proyecto.api.models.Employee;
import com.proyecto.api.models.EmployeeProjectId;
import com.proyecto.api.repositories.EmployeeProjectRepository;
import com.proyecto.api.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeProjectService employeeProjectService;

    public List<Employee> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()){
            throw new ResourceNotFoundException("Employees not found");
        }
        return employees;
    }
    public List<Employee> getAllActiveEmployees() {
        List<Employee> employees = employeeRepository.findByTerminationDateIsNull();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employees not found");
        }
        return employees;
    }
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + id));
    }
    public Employee getEmployeeActiveById(Integer id) {
        Employee activeEmployee= employeeRepository.findActiveEmployeeById(id);
        if(activeEmployee==null){
            throw new ResourceNotFoundException("Employee not found or Employee isn't active");
        }
        return activeEmployee;
    }
    public Employee saveEmployee(Employee newEmployee) {
        if(newEmployee==null){
            throw new InvalidDataException("Employee cannot be null");
        }
        try {
            return employeeRepository.save(newEmployee);
        } catch (Exception e) {
            throw new SaveException("Error saving employee: " + e.getMessage());
        }
    }
    public Employee updateDataEmployee(Integer id, Employee employee){
       Employee existingEmployee=getEmployeeById(id);
       if(existingEmployee !=null){
              existingEmployee.setName(employee.getName());
               existingEmployee.setNif(employee.getNif());
               existingEmployee.setSurname1(employee.getSurname1());
               existingEmployee.setSurname2(employee.getSurname2());
               existingEmployee.setBirthdate(employee.getBirthdate());
               existingEmployee.setPhone1(employee.getPhone1());
               existingEmployee.setPhone2(employee.getPhone2());
               existingEmployee.setEmail(employee.getEmail());
               existingEmployee.setHireDate(employee.getHireDate());
               existingEmployee.setMaritalStatus(employee.getMaritalStatus());
               existingEmployee.setMilitaryService(employee.getMilitaryService());

           try {
               return employeeRepository.save(existingEmployee);
           } catch (Exception e) {
               throw new SaveException("Error updating employee: " + e.getMessage());
           }
       }else{
           throw new ResourceNotFoundException("Employee with ID " + id + " not found");
       }
    }

    public Employee terminateEmployeeById(Integer employeeId){
        try {
            Employee existingEmployee = getEmployeeById(employeeId);

            // Comprueba si el empleado tiene proyectos asignados
            if (employeeProjectService.isEmployeeAssignedToAnyProject(employeeId)) {
                throw new ErrorEmployeeAssignedProjects("Employee is assigned a projects.");
            }

            // Establece la fecha de terminación
            existingEmployee.setTerminationDate(LocalDate.now());

            // Guarda los cambios
            return employeeRepository.save(existingEmployee);
        } catch (ErrorEmployeeAssignedProjects e) {
            throw e; // Propaga la excepción de empleado con proyectos asignados
        } catch (Exception e) {
            throw new SaveException("Error updating employee: " + e.getMessage());
        }
    }




}
