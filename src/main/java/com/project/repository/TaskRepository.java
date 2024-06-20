package com.project.repository;

import com.project.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    //dwukropkiem oznacza się parametry zapytania
    @Query("SELECT t FROM Task t WHERE t.project.projectId = :projectId")
    Page<Task> findProjectTask(@Param("projectId") Integer projectId, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE t.project.projectId = :projectId")
    List<Task> findProjectTasks(@Param("projectId") Integer projectId);
}

