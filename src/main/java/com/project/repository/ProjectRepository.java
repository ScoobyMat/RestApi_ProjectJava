package com.project.repository;

import java.util.List;

import com.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Project> findByNameContainingIgnoreCase(String name);

    // Metoda findByNazwaContainingIgnoreCase definiuje zapytanie
    // SELECT p FROM Projekt p WHERE upper(p.nazwa) LIKE upper(%:nazwa%)
}


