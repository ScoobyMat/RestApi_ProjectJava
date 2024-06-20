package com.project.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.project.model.Project;

public interface ProjectService {
    Optional<Project> getProject(Integer projectId);
    Project createProject(Project project);
    Project updateProject(Project project, Integer projectId);
    void deleteProject(Integer projectId);
    Page<Project> getProjects(Pageable pageable);
    Page<Project> searchByName(String name, Pageable pageable);
}
