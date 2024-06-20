package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Project",
        indexes = { @Index(name = "idx_name", columnList = "name", unique = false),
                @Index(name = "idx_description", columnList = "description", unique
                        = false) })
@Data
public class Project {
    @ManyToMany
    @JoinTable(name = "Project_Student",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="student_id")})
    private Set<Student> students;
    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    private List<Task> tasks;
    @Id
    @GeneratedValue
    @Column(name="project_id")
    private Integer projectId;
    private String name;
    private String description;
    private LocalDate deliveryDate;
    @CreationTimestamp
    @Column(name = "creation_data_time", nullable = false, updatable = false)
    private LocalDateTime creationDataTime;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreationDataTime() {
        return creationDataTime;
    }

    public void setCreationDataTime(LocalDateTime creationDataTime) {
        this.creationDataTime = creationDataTime;
    }

    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    @UpdateTimestamp
    @Column(name = "modification_data_time", nullable = false)
    private LocalDateTime modificationDateTime;
    public Project() {}
    public Project(Integer ProjectId, String name, String description,
                   LocalDateTime creationDataTime, LocalDate deliveryDate) {
        this.projectId = ProjectId;
        this.name = name;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.creationDataTime = creationDataTime;
    }
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

}