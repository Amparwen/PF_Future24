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
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * EmployeeService proporciona métodos para gestionar los empleados.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeProjectService employeeProjectService;


    /**
     * Obtiene todos los empleados.
     *
     * @return una lista de todos los empleados.
     * @throws ResourceNotFoundException si no se encuentran empleados.
     */
    public List<Employee> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()){
            throw new ResourceNotFoundException("No se han encontrado empleados");
        }
        return employees;
    }

    /**
     * Obtiene todos los empleados activos (aquellos sin fecha de terminación).
     *
     * @return una lista de empleados activos.
     * @throws ResourceNotFoundException si no se encuentran empleados activos.
     */
    public List<Employee> getAllActiveEmployees() {
        List<Employee> employees = employeeRepository.findByTerminationDateIsNull();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No se han encontrado empleados");
        }
        return employees;
    }

    /**
     * Obtiene un empleado por su ID.
     *
     * @param id el ID del empleado.
     * @return el empleado con el ID especificado.
     * @throws ResourceNotFoundException si no se encuentra el empleado con el ID especificado.
     */
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encuentra al empleado con este id  : " + id));
    }

    /**
     * Obtiene un empleado activo por su ID.
     *
     * @param id el ID del empleado.
     * @return el empleado activo con el ID especificado.
     * @throws ResourceNotFoundException si el empleado no se encuentra o está inactivo.
     */
    public Employee getEmployeeActiveById(Integer id) {
        Employee activeEmployee= employeeRepository.findActiveEmployeeById(id);
        if(activeEmployee==null){
            throw new ResourceNotFoundException("El empleado no se encuentra o está inactivo");
        }
        return activeEmployee;
    }

    /**
     * Guarda un nuevo empleado.
     *
     * @param newEmployee el nuevo empleado a guardar.
     * @return el empleado guardado.
     * @throws InvalidDataException si el empleado es nulo o su edad no es válida.
     * @throws SaveException si ocurre un error al guardar el empleado.
     */
    public Employee saveEmployee(Employee newEmployee) {
        try {
            if(newEmployee==null){
                throw new InvalidDataException("El empleado no puede ser nulo");
            }
            if (!ageValid(newEmployee.getBirthdate())) {
                throw new InvalidDataException("El empleado debe tener entre 18 y 67 años");
            }

            return employeeRepository.save(newEmployee);
        } catch (Exception e) {
            throw new SaveException("Error al guardar al empleado: " + e.getMessage());
        }
    }

/**
 * Actualiza los datos de un empleado existente.
 *
 * @param id       el ID del empleado a actualizar.
 * @param employee los nuevos datos del empleado.
 * @return el empleado actualizado.
 * @throws ResourceNotFoundException si el empleado no se encuentra.
 * @throws InvalidDataException si la edad del empleado no es válida.
 * @throws SaveException si ocurre un error al actualizar el empleado.
 */
    public Employee updateDataEmployee(Integer id, Employee employee){
       Employee existingEmployee=getEmployeeById(id);

       if(existingEmployee !=null){
           try {
              existingEmployee.setName(employee.getName());
               existingEmployee.setNif(employee.getNif());
               existingEmployee.setSurname1(employee.getSurname1());
               existingEmployee.setSurname2(employee.getSurname2());
               existingEmployee.setPhone1(employee.getPhone1());
               existingEmployee.setPhone2(employee.getPhone2());
               existingEmployee.setEmail(employee.getEmail());
               existingEmployee.setHireDate(employee.getHireDate());
               existingEmployee.setMaritalStatus(employee.getMaritalStatus());
               existingEmployee.setMilitaryService(employee.getMilitaryService());
                if(ageValid(employee.getBirthdate())){
                    existingEmployee.setBirthdate(employee.getBirthdate());
                }else{
                    throw new InvalidDataException("El empleado debe tener entre 18 y 67 años");
                }

               return employeeRepository.save(existingEmployee);
           } catch (Exception e) {
               throw new SaveException("Error al actualizar al empleado: " + e.getMessage());
           }
       }else{
           throw new ResourceNotFoundException("El empleado con ID " + id + " no ha sido encontrado");
       }
    }


    /**
     * Termina un empleado estableciendo su fecha de terminación a la fecha actual.
     *
     * @param employeeId el ID del empleado a terminar.
     * @return el empleado terminado.
     * @throws ErrorEmployeeAssignedProjects si el empleado tiene proyectos asignados.
     * @throws SaveException si ocurre un error al actualizar el empleado.
     */
    public Employee terminateEmployeeById(Integer employeeId){
        try {
            Employee existingEmployee = getEmployeeById(employeeId);

            // Comprueba si el empleado tiene proyectos asignados
            if (employeeProjectService.isEmployeeAssignedToAnyProject(employeeId)) {
                throw new ErrorEmployeeAssignedProjects("El empleado tiene proyectos asignados.");
            }

            // Establece la fecha de terminación
            existingEmployee.setTerminationDate(LocalDate.now());

            // Guarda los cambios
            return employeeRepository.save(existingEmployee);
        } catch (ErrorEmployeeAssignedProjects e) {
            throw e; // Propaga la excepción de empleado con proyectos asignados
        } catch (Exception e) {
            throw new SaveException("Error al actualizar al empleado: " + e.getMessage());
        }
    }

    /**
     * Verifica si la edad del empleado es válida (entre 18 y 67 años).
     *
     * @param birthday la fecha de nacimiento del empleado.
     * @return true si la edad es válida, de lo contrario false.
     */
    public boolean ageValid(LocalDate birthday){
        if (birthday==null){
            return  false;
        }
        int age = Period.between(birthday, LocalDate.now()).getYears();
        return age >=18 && age <=67;
    }

}
