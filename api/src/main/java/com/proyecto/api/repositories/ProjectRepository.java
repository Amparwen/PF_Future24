package com.proyecto.api.repositories;

import com.proyecto.api.models.Employee;
import com.proyecto.api.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
   List<Project> findByTerminationDateIsNull();

   @Query("SELECT p FROM PR_PROYECTOS p WHERE p.idProject = ?1 AND p.terminationDate IS NULL")
   Project findActiveProjectById(Integer id);
}
