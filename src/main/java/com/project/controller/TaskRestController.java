package com.project.controller;

import com.project.model.Task;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {
    private final TaskService taskService;
    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        Optional<Task> task = taskService.getTask(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.setTask(task);
        return ResponseEntity.ok(createdTask);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody
    Task taskDetails) {
        Optional<Task> optionalTask = taskService.getTask(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(taskDetails.getName());
            task.setTaskOrder(taskDetails.getTaskOrder());
            task.setDescription(taskDetails.getDescription());
            Task updatedTask = taskService.setTask(task);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        Optional<Task> optionalTask = taskService.getTask(id);
        if (optionalTask.isPresent()) {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<Task>> getTasks(Pageable pageable) {
        Page<Task> tasks = taskService.getTasks(pageable);
        return ResponseEntity.ok(tasks);
    }
    /*@GetMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(@RequestParam String name,
                                                  Pageable pageable) {
        Page<Task> tasks = taskService.searchByName(name, pageable);
        return ResponseEntity.ok(tasks);
    }*/
}
