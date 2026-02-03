package com.example.studentmaneger.controller;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        if (studentService.getStudentById(student.getId()).isPresent()) {
            return "redirect:/students/add?error=exists";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 4. HIỂN THỊ FORM CẬP NHẬT (Lấy dữ liệu cũ đổ vào Form)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, @RequestParam(required = false) String error, Model model) {
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
        if (studentService.getStudentById(student.getId()).isEmpty()) {
            return "redirect:/students/edit/" + student.getId() + "?error=notfound";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // 5. XÓA: Xử lý xóa theo ID (POST)
    @PostMapping("/delete/{id}")
    public String deleteStudentPost(@PathVariable int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}