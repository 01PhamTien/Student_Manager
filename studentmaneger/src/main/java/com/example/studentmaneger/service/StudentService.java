package com.example.studentmaneger.service;

import com.example.studentmaneger.entity.Student;
import com.example.studentmaneger.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // 1. Lấy tất cả sinh viên từ SQL Server
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // 2. Lấy sinh viên theo ID (Dùng cho chức năng Sửa/Xem chi tiết)
    public Optional<Student> getStudentById(UUID id) {
        return repository.findById(id);
    }

    // 3. Lưu hoặc Cập nhật sinh viên
    // JpaRepository tự hiểu: nếu đối tượng có ID đã tồn tại sẽ là Update, nếu ID
    // mới sẽ là Insert
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    // 4. Xóa sinh viên theo ID
    public void deleteStudent(UUID id) {
        repository.deleteById(id);
    }

    // Tìm kiếm sinh viên theo tên (contains ignore case)
    public List<Student> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // Tìm kiếm sinh viên theo danh sách ID
    public List<Student> findByIds(List<UUID> ids) {
        return repository.findByIdIn(ids);
    }

    // Tìm và lọc theo nhiều tiêu chí: q có thể là tên; minAge/maxAge để lọc tuổi
    public List<Student> searchStudents(String q, Integer minAge, Integer maxAge, String sort) {
        List<Student> results;

        // Chỉ tìm kiếm theo tên (bỏ tìm theo ID vì UUID không còn phù hợp)
        if (q != null && !q.isBlank()) {
            results = repository.findByNameContainingIgnoreCase(q.trim());
        } else {
            results = repository.findAll();
        }

        // apply age filters in memory for simplicity
        if (minAge != null || maxAge != null) {
            results = results.stream()
                    .filter(s -> (minAge == null || s.getAge() >= minAge) &&
                            (maxAge == null || s.getAge() <= maxAge))
                    .toList();
        }

        // simple sort
        if (sort != null) {
            if (sort.equals("name")) {
                results = results.stream().sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName())).toList();
            } else if (sort.equals("age")) {
                results = results.stream().sorted((a, b) -> Integer.compare(a.getAge(), b.getAge())).toList();
            }
        }

        return results;
    }
}
