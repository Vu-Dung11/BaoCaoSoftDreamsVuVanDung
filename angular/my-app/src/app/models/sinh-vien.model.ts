export interface SinhVien {
  ma_sv: number;
  ten_sv: string;
  gioi_tinh: string;
  ngay_sinh: string; // Dữ liệu trả về là chuỗi ngày tháng
  lop: string;
  khoa: string;
  createdAt: string;
  updatedAt: any; // Có thể null nên để any hoặc string | null
}
