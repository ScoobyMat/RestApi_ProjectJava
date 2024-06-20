package com.project.service;

import com.project.model.Project;
import com.project.model.Task;
import com.project.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> getTask(Integer taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Task setTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task updateTask(Task task, Integer taskId) {
        task.setTaskId(taskId);
        taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Project> searchByName(String name, Pageable pageable) {
        return null;
    }
}
