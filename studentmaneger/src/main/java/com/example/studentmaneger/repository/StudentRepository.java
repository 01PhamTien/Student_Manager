package com.example.studentmaneger.repository;

import com.example.studentmaneger.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByIdIn(List<UUID> ids);

    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}
