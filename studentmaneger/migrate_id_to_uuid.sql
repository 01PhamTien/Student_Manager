-- Script đổi cột ID từ INT sang UNIQUEIDENTIFIER (UUID)
-- Chạy script này trong SQL Server để cập nhật database

USE school;
GO

-- Cách 1: Nếu bảng chưa có dữ liệu hoặc có thể xóa
-- Xóa bảng cũ (nếu có)
IF OBJECT_ID('dbo.students', 'U') IS NOT NULL
    DROP TABLE dbo.students;
GO

-- Hibernate sẽ tự tạo bảng mới với kiểu UNIQUEIDENTIFIER khi chạy app
-- Không cần tạo thủ công

-- Cách 2: Nếu muốn giữ dữ liệu cũ (không khuyến khích vì ID kiểu mới không tương thích)
-- Thêm cột mới
-- ALTER TABLE students ADD id_new UNIQUEIDENTIFIER NULL;
-- Cập nhật cột mới với UUID mới
-- UPDATE students SET id_new = NEWID();
-- Xóa cột cũ
-- ALTER TABLE students DROP COLUMN id;
-- Đổi tên cột mới
-- EXEC sp_rename 'students.id_new', 'id', 'COLUMN';
GO

PRINT 'Script hoàn thành. Hãy khởi động lại ứng dụng Spring Boot.';

