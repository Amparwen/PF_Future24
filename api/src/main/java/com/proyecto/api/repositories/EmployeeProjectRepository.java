package com.proyecto.api.repositories;

import com.proyecto.api.models.EmployeeProject;
import com.proyecto.api.models.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeProjectRepository proporciona métodos CRUD y consultas personalizadas
 * para la entidad EmployeeProject.
 */
@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
    //ep.employee.id = employee ->  @ManyToOne
    //                              @MapsId("employeeId")
    //                              @JoinColumn(name = "ID_EMPLEADO")
    //                              private Employee employee;

    /**
     * Verifica si existe una relación EmployeeProject con el ID del empleado especificado.
     * @param employeeId el ID del empleado.
     * @return true si existe una relación EmployeeProject con el ID del empleado especificado, de lo contrario false.
     */
    @Query("SELECT COUNT(ep) > 0 FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.employee.id = ?1")
    boolean existsByEmployeeId(Integer employeeId);


    /**
     * Verifica si existe una relación EmployeeProject con el ID del proyecto especificado.
     * @param projectId el ID del proyecto.
     * @return true si existe una relación EmployeeProject con el ID del proyecto especificado, de lo contrario false.
     */
    @Query("SELECT COUNT(ep) > 0 FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.project.id = ?1")
    boolean existsByProjectId(Integer projectId);


    /**
     * Encuentra los IDs de los empleados asociados con el ID del proyecto especificado.
     * @param projectId el ID del proyecto.
     * @return una lista de IDs de empleados asociados con el proyecto.
     */
    @Query("SELECT ep.id.employeeId FROM PR_EMPLEADOS_PROYECTO ep WHERE ep.project.id = ?1")
    List<Integer> findEmployeeIdsByProjectId(Integer projectId);


}
