package com.proyecto.api.repositories;

import com.proyecto.api.models.Employee;
import com.proyecto.api.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * ProjectRepository proporciona métodos CRUD y consultas personalizadas
 * para la entidad Project.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
   /**
    * Encuentra todos los proyectos que no tienen una fecha de terminación (proyectos activos).
    * @return una lista de proyectos activos.
    */
   List<Project> findByTerminationDateIsNull();


   /**
    * Encuentra un proyecto activo por su ID.
    * @param id el ID del proyecto.
    * @return el proyecto activo con el ID especificado, o null si no se encuentra.
    */
   @Query("SELECT p FROM PR_PROYECTOS p WHERE p.idProject = ?1 AND p.terminationDate IS NULL")
   Project findActiveProjectById(Integer id);
}
