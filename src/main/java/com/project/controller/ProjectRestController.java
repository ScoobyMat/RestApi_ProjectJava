package com.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.model.Project;
import com.project.service.ProjectService;

// dzięki adnotacji @RestController klasa jest traktowana jako zarządzany
@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private final ProjectService projectService;
    @Autowired
    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Integer id) {
        Optional<Project> project = projectService.getProject(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return ResponseEntity.ok(createdProject);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer id,
                                                 @RequestBody Project projectDetails) {
        Optional<Project> optionalProject = projectService.getProject(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(projectDetails.getName());
            project.setDescription(projectDetails.getDescription());
            project.setDeliveryDate(projectDetails.getDeliveryDate());
            Project updatedProject = projectService.createProject(project);
            return ResponseEntity.ok(updatedProject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        Optional<Project> optionalProject = projectService.getProject(id);
        if (optionalProject.isPresent()) {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<Project>> getProjects(Pageable pageable) {
        Page<Project> projects = projectService.getProjects(pageable);
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<Project>> searchProjects(@RequestParam String name,
                                                        Pageable pageable) {
        Page<Project> projects = projectService.searchByName(name, pageable);
        return ResponseEntity.ok(projects);
    }
}
