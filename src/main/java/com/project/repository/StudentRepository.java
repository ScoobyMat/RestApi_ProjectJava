package com.project.repository;

import java.util.Optional;

import com.project.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByIndexNumber(String indexNumber);

    Page<Student> findByIndexNumberStartsWith(String indexNumber, Pageable pageable);
    Page<Student> findByLastnameStartsWithIgnoreCase(String lastName, Pageable pageable);
    Optional<Student> findByEmail(String email);

    // Metoda findByNrIndeksuStartsWith definiuje zapytanie
    // SELECT s FROM Student s WHERE s.nrIndeksu LIKE :nrIndeksu%

    // Metoda findByNazwiskoStartsWithIgnoreCase definiuje zapytanie
    // SELECT s FROM Student s WHERE upper(s.nazwisko) LIKE upper(:nazwisko%)
}

