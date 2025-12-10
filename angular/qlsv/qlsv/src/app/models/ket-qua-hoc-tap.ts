export interface KetQuaHocTap {
  id: number;
  diem_qua_trinh: number;
  diem_thanh_phan: number;
  sinhVienTen: string;
  monHocTen: string;

  // --- THÊM 2 DÒNG NÀY ---
  sinhVienId: number;
  monHocId: number;

  // Các trường phụ (có thể có hoặc không)
  createdAt?: string;
  updatedAt?: string;
}
