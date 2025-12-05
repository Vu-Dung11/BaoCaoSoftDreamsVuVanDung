-- Script tạo cơ sở dữ liệu và các bảng cho hệ thống quản lý sinh viên
-- Chạy script này trong MySQL để tạo database và các bảng cần thiết

-- Tạo database (nếu chưa tồn tại)
CREATE DATABASE IF NOT EXISTS quanlysinhvien;
USE quanlysinhvien;

-- Tạo bảng sinh_viên
CREATE TABLE IF NOT EXISTS sinh_vien (
    ma_sv BIGINT PRIMARY KEY,
    ten_sv VARCHAR(255) NOT NULL,
    gioi_tinh VARCHAR(10),
    ngay_sinh VARCHAR(50),
    lop VARCHAR(50),
    khoa VARCHAR(50)
);

-- Tạo bảng mon_hoc
CREATE TABLE IF NOT EXISTS mon_hoc (
    ma_mon INT PRIMARY KEY AUTO_INCREMENT,
    ten_mon VARCHAR(255) NOT NULL,
    ty_le_diem_qua_trinh DOUBLE NOT NULL DEFAULT 0.3,
    CHECK (ty_le_diem_qua_trinh >= 0 AND ty_le_diem_qua_trinh <= 1)
);

-- Tạo bảng ket_qua_hoc_tap
CREATE TABLE IF NOT EXISTS ket_qua_hoc_tap (
    ma_sv BIGINT NOT NULL,
    ma_mon INT NOT NULL,
    diem_qua_trinh DOUBLE DEFAULT 0,
    diem_thanh_phan DOUBLE DEFAULT 0,
    PRIMARY KEY (ma_sv, ma_mon),
    FOREIGN KEY (ma_sv) REFERENCES sinh_vien(ma_sv) ON DELETE CASCADE,
    FOREIGN KEY (ma_mon) REFERENCES mon_hoc(ma_mon) ON DELETE CASCADE
);

-- Thêm dữ liệu mẫu (tùy chọn)
-- Thêm một số sinh viên mẫu
INSERT INTO sinh_vien (ma_sv, ten_sv, gioi_tinh, ngay_sinh, lop, khoa) VALUES
(1, 'Nguyễn Văn A', 'Nam', '2000-01-15', 'CNTT01', 'K2020'),
(2, 'Trần Thị B', 'Nữ', '2001-03-20', 'CNTT01', 'K2021'),
(3, 'Lê Văn C', 'Nam', '2000-07-10', 'CNTT02', 'K2020');

-- Thêm một số môn học mẫu
INSERT INTO mon_hoc (ten_mon, ty_le_diem_qua_trinh) VALUES
('Lập trình Java', 0.3),
('Cơ sở dữ liệu', 0.4),
('Cấu trúc dữ liệu', 0.3),
('Lập trình Web', 0.3);

-- Thêm một số kết quả học tập mẫu
INSERT INTO ket_qua_hoc_tap (ma_sv, ma_mon, diem_qua_trinh, diem_thanh_phan) VALUES
(1, 1, 8.5, 7.0),
(1, 2, 9.0, 8.5),
(2, 1, 7.5, 8.0),
(2, 3, 8.0, 7.5),
(3, 2, 9.5, 9.0);

-- Hiển thị thông báo
SELECT 'Database và các bảng đã được tạo thành công!' AS Message;
SELECT 'Dữ liệu mẫu đã được thêm vào!' AS Message;

