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
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }
    @GetMapping("/active")
    public List<Employee> getAllActiveEmployees() {
        return employeeService.getAllActiveEmployees();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@Valid @PathVariable int id) {
            return employeeService.getEmployeeById(id);
    }
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    @PutMapping ("/{id}")
    public Employee updateDataEmployee(@PathVariable int id, @Valid @RequestBody Employee employee) {
        return employeeService.updateDataEmployee(id, employee);
    }

    @PutMapping ("/terminate/{id}")
    public Employee terminateEmployeeById(@Valid @PathVariable int id) {
        return employeeService.terminateEmployeeById(id);
    }

}
