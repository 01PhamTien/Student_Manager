-- Script tạo 10 dữ liệu mẫu sinh viên
-- Chạy sau khi đã DROP TABLE và khởi động app để Hibernate tạo bảng mới

USE school;
GO

-- Chèn 10 sinh viên mẫu (ID sẽ được tạo tự động bởi Hibernate khi INSERT)
-- Lưu ý: Khi dùng Hibernate với GenerationType.UUID, giá trị ID sẽ được tạo tự động

INSERT INTO students (name, age, email) VALUES 
(N'Nguyễn Văn A', 20, 'nguyenvana@email.com'),
(N'Trần Thị B', 21, 'tranthis@email.com'),
(N'Lê Hoàng C', 19, 'lehoangc@email.com'),
(N'Phạm Minh D', 22, 'phammind@email.com'),
(N'Vũ Thị E', 20, 'vuthie@email.com'),
(N'Đặng Văn F', 23, 'dangvanf@email.com'),
(N'Bùi Thị G', 18, 'buithig@email.com'),
(N'Ngô Văn H', 24, 'ngovanh@email.com'),
(N'Đỗ Thị I', 19, 'dothii@email.com'),
(N'Chu Văn J', 21, 'chuvanj@email.com');
GO

SELECT * FROM students;
GO

PRINT 'Đã chèn 10 dữ liệu mẫu thành công!';

