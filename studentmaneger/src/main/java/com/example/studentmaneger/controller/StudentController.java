package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 1. ĐỌC: Hiển thị danh sách sinh viên
    @GetMapping
    public String listStudents(@RequestParam(required = false) String q,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String sort,
            Model model) {
        List<Student> students = studentService.searchStudents(q, minAge, maxAge, sort);
        model.addAttribute("students", students);
        model.addAttribute("q", q);
        model.addAttribute("minAge", minAge);
        model.addAttribute("maxAge", maxAge);
        model.addAttribute("sort", sort);
        return "students";
    }

    // 2. HIỂN THỊ FORM THÊM MỚI
    @GetMapping("/add")
    public String showAddForm(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("isNew", true);
        model.addAttribute("error", error);
        return "addStudent";

    }

    // XỬ LÝ THÊM SINH VIÊN (POST)
    // ID sẽ được tự động tạo bởi hệ thống, không cần kiểm tra trùng
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        // Không cần kiểm tra ID trùng vì UUID tự động tạo
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 4. HIỂN THỊ FORM CẬP NHẬT (Lấy dữ liệu cũ đổ vào Form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, @RequestParam(required = false) String error, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không hợp lệ: " + id));

        model.addAttribute("student", student);
        model.addAttribute("isNew", false);
        model.addAttribute("error", error);
        return "addStudent"; // Dùng chung giao diện với trang thêm
    }

    // XỬ LÝ CẬP NHẬT SINH VIÊN (POST)
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {
        // JpaRepository.save() sẽ tự động update nếu ID tồn tại
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 5. XÓA: Xử lý xóa theo ID (POST)
    @PostMapping("/delete/{id}")
    public String deleteStudentPost(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
