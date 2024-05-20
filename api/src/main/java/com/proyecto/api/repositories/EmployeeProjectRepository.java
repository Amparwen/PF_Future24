package com.proyecto.api.repositories;

import com.proyecto.api.models.EmployeeProject;
import com.proyecto.api.models.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
    //ep.employee.id = employee ->  @ManyToOne
    //                              @MapsId("employeeId")
    //                              @JoinColumn(name = "ID_EMPLEADO")
    //                              private Employee employee;
    @Query("SELECT COUNT(ep) > 0 FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.employee.id = ?1")
    boolean existsByEmployeeId(Integer employeeId);

    @Query("SELECT COUNT(ep) > 0 FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.project.id = ?1")
    boolean existsByProjectId(Integer projectId);

    @Query("SELECT ep.id.employeeId FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.project.id = ?1")
    List<Integer> findEmployeeIdsByProjectId(Integer projectId);


}
