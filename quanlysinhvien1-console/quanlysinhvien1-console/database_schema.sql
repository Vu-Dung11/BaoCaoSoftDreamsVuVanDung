-- ============================================
-- TẠO DATABASE
-- ============================================
CREATE DATABASE IF NOT EXISTS quanlysinhvien;
USE quanlysinhvien;

-- ============================================
-- TẠO BẢNG MÔN HỌC (MonHoc)
-- ============================================
CREATE TABLE IF NOT EXISTS mon_hoc (
    ma_mon INT AUTO_INCREMENT PRIMARY KEY,
    ten_mon VARCHAR(100) NOT NULL UNIQUE,
    ty_le_diem_qua_trinh DECIMAL(3,2) NOT NULL,
    INDEX idx_ten_mon (ten_mon)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TẠO BẢNG SINH VIÊN (SinhVien)
-- ============================================
CREATE TABLE IF NOT EXISTS sinh_vien (
    ma_sv BIGINT PRIMARY KEY,
    ten_sv VARCHAR(100) NOT NULL,
    gioi_tinh VARCHAR(10) NOT NULL,
    ngay_sinh VARCHAR(20),
    lop VARCHAR(50),
    khoa VARCHAR(50),
    INDEX idx_ten_sv (ten_sv),
    INDEX idx_lop (lop),
    INDEX idx_khoa (khoa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TẠO BẢNG KẾT QUẢ HỌC TẬP (KetQuaHocTap)
-- ============================================
CREATE TABLE IF NOT EXISTS ket_qua_hoc_tap (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ma_sv BIGINT NOT NULL,
    ma_mon INT NOT NULL,
    diem_qua_trinh DECIMAL(4,2) DEFAULT 0.00,
    diem_thanh_phan DECIMAL(4,2) DEFAULT 0.00,
    UNIQUE KEY unique_sv_mon (ma_sv, ma_mon),
    INDEX idx_ma_sv (ma_sv),
    INDEX idx_ma_mon (ma_mon)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TẠO CÁC RÀNG BUỘC KHÓA NGOẠI (FOREIGN KEY)
-- ============================================

-- Khóa ngoại từ ket_qua_hoc_tap đến sinh_vien
ALTER TABLE ket_qua_hoc_tap
ADD CONSTRAINT fk_ket_qua_sinh_vien
FOREIGN KEY (ma_sv) 
REFERENCES sinh_vien(ma_sv)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Khóa ngoại từ ket_qua_hoc_tap đến mon_hoc
ALTER TABLE ket_qua_hoc_tap
ADD CONSTRAINT fk_ket_qua_mon_hoc
FOREIGN KEY (ma_mon) 
REFERENCES mon_hoc(ma_mon)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Lưu ý: 
-- - CHECK constraint chỉ hỗ trợ từ MySQL 8.0.16 trở lên
-- - Nếu dùng MySQL < 8.0.16, có thể thêm trigger hoặc kiểm tra ở application layer

-- ============================================
-- CHÈN DỮ LIỆU MẪU (Tùy chọn)
-- ============================================

-- Chèn dữ liệu môn học
INSERT INTO mon_hoc (ten_mon, ty_le_diem_qua_trinh) VALUES
('Lập trình Java', 0.40),
('Cấu trúc dữ liệu', 0.30),
('Cơ sở dữ liệu', 0.35),
('Hệ điều hành', 0.25);

-- Chèn dữ liệu sinh viên
INSERT INTO sinh_vien (ma_sv, ten_sv, gioi_tinh, ngay_sinh, lop, khoa) VALUES
(1, 'Nguyễn Văn An', 'Nam', '10/02/2004', 'CNTT1', 'K17'),
(2, 'Trần Thị Bình', 'Nữ', '23/05/2004', 'CNTT2', 'K17'),
(3, 'Lê Hữu Cường', 'Nam', '01/09/2003', 'CNTT3', 'K16');

-- Chèn dữ liệu kết quả học tập
-- Lưu ý: ma_mon phải khớp với ID trong bảng mon_hoc
INSERT INTO ket_qua_hoc_tap (ma_sv, ma_mon, diem_qua_trinh, diem_thanh_phan) VALUES
-- Sinh viên 1 (Nguyễn Văn An)
(1, 1, 7.50, 8.00),  -- Lập trình Java
(1, 2, 6.50, 7.00),  -- Cấu trúc dữ liệu
-- Sinh viên 2 (Trần Thị Bình)
(2, 1, 8.00, 8.50),  -- Lập trình Java
(2, 3, 7.00, 6.50),  -- Cơ sở dữ liệu
(2, 4, 6.00, 7.50),  -- Hệ điều hành
-- Sinh viên 3 (Lê Hữu Cường)
(3, 2, 5.50, 5.00),  -- Cấu trúc dữ liệu
(3, 3, 4.50, 6.00);  -- Cơ sở dữ liệu

-- ============================================
-- KIỂM TRA DỮ LIỆU
-- ============================================
-- SELECT * FROM sinh_vien;
-- SELECT * FROM mon_hoc;
-- SELECT * FROM ket_qua_hoc_tap;

