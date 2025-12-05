
import { SinhVienService } from './../../services/sinh-vien.service';
import { Component, OnInit } from '@angular/core';
import { SinhVien } from '../../models/sinh-vien.model';

@Component({
  selector: 'app-viewlistsv',
  templateUrl: './viewlistsv.component.html',
  styleUrl: './viewlistsv.component.css'
})
export class ViewlistsvComponent implements OnInit {

  danhsachsv: SinhVien[] = [];

  constructor(private sinhVienService: SinhVienService) { }

  ngOnInit(): void {
    this.layDanhSachSinhVien();
  }
layDanhSachSinhVien() {
    this.sinhVienService.getSinhViens().subscribe({
      next: (data) => {
        this.danhsachsv = data;
        console.log('Dữ liệu lấy về:', data); // Kiểm tra log trên trình duyệt
      },
      error: (e) => {
        console.error('Lỗi khi gọi API:', e);
      }
    });
  }
}
