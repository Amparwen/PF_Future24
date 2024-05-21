package com.proyecto.api.controller;

import com.proyecto.api.models.Employee;
import com.proyecto.api.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
/**
 * Controlador los empleados
 */
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Recupera todos los empleados.
     *
     * @return una lista de todos los empleados.
     */
    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    /**
     * Recupera todos los empleados activos.
     *
     * @return una lista de empleados activos.
     */
    @GetMapping("/active")
    public List<Employee> getAllActiveEmployees() {
        return employeeService.getAllActiveEmployees();
    }

    /**
     * Recupera un empleado por su ID.
     *
     * @param id el ID del empleado.
     * @return el empleado con el ID proporcionado.
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@Valid @PathVariable int id) {
            return employeeService.getEmployeeById(id);
    }

    /**
     * Crea un nuevo empleado.
     *
     * @param employee el empleado a crear.
     * @return un ResponseEntity que contiene el empleado guardado y el estado HTTP CREATED.
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de un empleado existente.
     *
     * @param id el ID del empleado a actualizar.
     * @param employee el empleado con los nuevos datos.
     * @return el empleado actualizado.
     */
    @PutMapping ("/{id}")
    public Employee updateDataEmployee(@PathVariable int id, @Valid @RequestBody Employee employee) {
        return employeeService.updateDataEmployee(id, employee);
    }

    /**
     * Termina (desactiva) un empleado por su ID.
     *
     * @param id el ID del empleado a terminar.
     * @return el empleado terminado.
     */
    @PutMapping ("/terminate/{id}")
    public Employee terminateEmployeeById(@Valid @PathVariable int id) {
        return employeeService.terminateEmployeeById(id);
    }

}
