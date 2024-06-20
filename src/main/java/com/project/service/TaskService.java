package com.project.service;

import com.project.model.Project;
import com.project.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface TaskService {
    Optional<Task> getTask(Integer taskId);
    Task setTask(Task task);
    Task updateTask(Task task, Integer taskId);
    void deleteTask(Integer taskId);
    Page<Task> getTasks(Pageable pageable);
    Page<Project> searchByName(String name, Pageable pageable);
}
