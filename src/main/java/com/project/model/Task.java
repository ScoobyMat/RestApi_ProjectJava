package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Data
public class Task {
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"task"})
    private Project project;
    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private Integer taskId;
    @Column(nullable = false, length = 50)
    private String name;
    private Integer taskOrder;
    @Column(length = 1000)
    private String description;
    @CreationTimestamp
    @Column(name = "creation_data_time", nullable = false, updatable = false)
    private LocalDateTime creationDataTime;
    @UpdateTimestamp
    @Column(name = "modification_data_time", nullable = false)
    private LocalDateTime modificationDataTime;
    public Task() {
    }
    public Task(String name, Integer taskOrder, String description) {
        this.name = name;
        this.taskOrder = taskOrder;
        this.description = description;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(Integer taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDataTime() {
        return creationDataTime;
    }

    public void setCreationDataTime(LocalDateTime creationDataTime) {
        this.creationDataTime = creationDataTime;
    }

    public LocalDateTime getModificationDataTime() {
        return modificationDataTime;
    }

    public void setModificationDataTime(LocalDateTime modificationDataTime) {
        this.modificationDataTime = modificationDataTime;
    }

}
