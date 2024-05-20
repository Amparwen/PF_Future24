package com.proyecto.api.repositories;

import com.proyecto.api.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByTerminationDateIsNull();

    @Query("SELECT e FROM EM_EMPLEADOS e WHERE e.idEmployee = ?1 AND e.terminationDate IS NULL")
    Employee findActiveEmployeeById(Integer id);
}
