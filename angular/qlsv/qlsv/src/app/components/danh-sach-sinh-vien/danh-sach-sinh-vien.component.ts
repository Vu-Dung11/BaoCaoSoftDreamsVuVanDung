import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Import Forms

import { SinhVienService } from '../../services/sinh-vien.service';
import { SinhVien } from '../../models/sinh-vien'; // Chú ý đường dẫn file model cho đúng

@Component({
  selector: 'app-danh-sach-sinh-vien',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './danh-sach-sinh-vien.component.html',
  styleUrls: ['./danh-sach-sinh-vien.component.css']
})
export class DanhSachSinhVienComponent implements OnInit {
  danhSachSV: SinhVien[] = [];
  keyword: string = '';

  constructor(private svService: SinhVienService) { }

  ngOnInit(): void {
    // --- LỖI CỦA BẠN Ở ĐÂY ---
    // SAI: this.layDanhSach
    // ĐÚNG: Phải có dấu () để thực thi hàm
    this.layDanhSach();
  }

  layDanhSach() {
    this.svService.getDanhSach().subscribe({
      next: (data) => this.danhSachSV = data,
      error: (err) => console.error(err)
    });
  }

  onSearch() {
    console.log('Từ khóa tìm kiếm:');
    // 1. Nếu từ khóa rỗng hoặc chỉ toàn dấu cách -> Load lại danh sách gốc
    if (!this.keyword || this.keyword.trim() === '') {
      this.layDanhSach();
      return;
    }

    // 2. Gọi API tìm kiếm
    this.svService.timKiemSinhVien(this.keyword).subscribe({
      next: (data) => {
        this.danhSachSV = data;
      },
      error: (err) => {
        console.error('Lỗi tìm kiếm:', err);
      }
    });
  }
}
