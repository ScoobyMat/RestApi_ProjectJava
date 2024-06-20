package com.project.service;

import java.util.Optional;
import com.project.model.Task;
import com.project.repository.ProjectRepository;
import com.project.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.project.model.Project;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository
            taskRepo) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepo;
    }

    @Override
    public Optional<Project> getProject(Integer projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project, Integer projectId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteProject(Integer projectId) {
        taskRepository.deleteAll(taskRepository.findProjectTasks(projectId));
        projectRepository.deleteById(projectId);
    }

    @Override
    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> searchByName(String name, Pageable pageable) {
        return projectRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}