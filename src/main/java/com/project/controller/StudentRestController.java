package com.project.controller;

import com.project.model.Student;
import com.project.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer id) {
        Optional<Student> student = studentService.getStudent(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.setStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id,
                                                 @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentService.getStudent(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setFirstname(studentDetails.getFirstname());
            student.setLastname(studentDetails.getLastname());
            student.setIndexNumber(studentDetails.getIndexNumber());
            student.setEmail(studentDetails.getEmail());
            student.setUsername(studentDetails.getUsername());
            student.setPassword(studentDetails.getPassword());
            student.setRole(studentDetails.getRole());
            student.setStationary(studentDetails.isStationary());
            Student updatedStudent = studentService.setStudent(student);
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        Optional<Student> optionalStudent = studentService.getStudent(id);
        if (optionalStudent.isPresent()) {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Student>> getStudents(Pageable pageable) {
        Page<Student> students = studentService.getStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Student>> searchStudents(@RequestParam String lastname, Pageable pageable) {
        Page<Student> students = studentService.findByLastname(lastname, pageable);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/email")
    public ResponseEntity<Student> searchByEmail(@RequestParam String email) {
        Optional<Student> student = studentService.findByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
