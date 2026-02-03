package com.example.studentmaneger.repository;

import com.example.studentmaneger.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByIdIn(List<Integer> ids);

    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);
}