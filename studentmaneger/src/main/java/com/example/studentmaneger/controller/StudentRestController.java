package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student API", description = "API quản lý sinh viên")
public class StudentRestController {

    @Autowired
    private StudentService studentService;

    // 1. LẤY TẤT CẢ SINH VIÊN
    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả sinh viên", description = "Trả về danh sách tất cả sinh viên")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // 2. LẤY SINH VIÊN THEO ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin sinh viên theo ID", description = "Trả về thông tin chi tiết của một sinh viên")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. THÊM MỚI SINH VIÊN
    @PostMapping
    @Operation(summary = "Thêm sinh viên mới", description = "Tạo mới một sinh viên")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // ID sẽ được tự động tạo bởi hệ thống
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // 4. CẬP NHẬT SINH VIÊN
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin sinh viên", description = "Cập nhật thông tin của một sinh viên theo ID")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            if (studentDetails.getName() != null) {
                student.setName(studentDetails.getName());
            }
            if (studentDetails.getAge() > 0) {
                student.setAge(studentDetails.getAge());
            }
            if (studentDetails.getEmail() != null) {
                student.setEmail(studentDetails.getEmail());
            }
            Student updatedStudent = studentService.saveStudent(student);
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.notFound().build();
    }

    // 5. XÓA SINH VIÊN
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa sinh viên", description = "Xóa một sinh viên theo ID")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
