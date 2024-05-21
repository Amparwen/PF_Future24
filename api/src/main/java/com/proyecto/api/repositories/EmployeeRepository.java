package com.proyecto.api.repositories;

import com.proyecto.api.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeRepository proporciona métodos CRUD y consultas personalizadas
 * para la entidad Employee.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    /**
     * Encuentra todos los empleados que no tienen una fecha de terminación (empleados activos).
     * @return una lista de empleados activos.
     */
    List<Employee> findByTerminationDateIsNull();

    /**
     * Encuentra un empleado activo por su ID.
     * @param id el ID del empleado.
     * @return el empleado activo con el ID especificado, o null si no se encuentra.
     */
    @Query("SELECT e FROM EM_EMPLEADOS e WHERE e.idEmployee = ?1 AND e.terminationDate IS NULL")
    Employee findActiveEmployeeById(Integer id);
}
