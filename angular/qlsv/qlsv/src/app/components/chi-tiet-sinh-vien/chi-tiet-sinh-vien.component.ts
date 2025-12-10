import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import { SinhVienService } from '../../services/sinh-vien.service';
import { SinhVien } from '../../models/sinh-vien';
import { NgZone } from '@angular/core';
import { KetQuaHocTapComponent } from '../ket-qua-hoc-tap/ket-qua-hoc-tap.component';
import { KetQuaTruotDoComponent } from '../ket-qua-truot-do/ket-qua-truot-do.component';

@Component({
  selector: 'app-chi-tiet-sinh-vien',
  standalone: true,
  imports: [CommonModule, RouterLink, DatePipe, KetQuaHocTapComponent, KetQuaTruotDoComponent],
  templateUrl: './chi-tiet-sinh-vien.component.html',
  styleUrls: ['./chi-tiet-sinh-vien.component.css']
})
export class ChiTietSinhVienComponent implements OnInit {
  sinhVien: SinhVien | null = null;
  isLoading = true;
  hasError = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private svService: SinhVienService,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    console.log('=== CHI TIET SINH VIEN COMPONENT INIT ===');
    console.log('Component created for route:', this.router.url);

    this.isLoading = true;
    this.hasError = false;
    this.errorMessage = '';

    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log('ID from route:', id);

    if (id) {
      console.log('Calling API for student ID:', id);
      console.log('API URL will be:', `http://localhost:8080/api/sinh-vien/${id}`);

      this.svService.getChiTiet(id).subscribe({
        next: (data) => {
          console.log('API Response received:', data);


          this.sinhVien = data;

          this.isLoading = false;
          console.log('Student data set to component, sinhVien is now:', this.sinhVien);
          console.log('sinhVien.ma_sv:', this.sinhVien?.ma_sv);
        },
        error: (err) => {
          console.error('API Error details:', err);
          console.error('Error status:', err.status);
          console.error('Error message:', err.message);
          console.error('Error URL:', err.url);

          this.isLoading = false;
          this.hasError = true;
          this.errorMessage = `Không thể tải dữ liệu sinh viên. Lỗi: ${err.status} - ${err.message}`;
          this.sinhVien = null;
        }
      });
    } else {
      console.error('No ID found in route - cannot load student details');
      this.isLoading = false;
      this.hasError = true;
      this.errorMessage = 'Không tìm thấy mã sinh viên trong URL';
      this.sinhVien = null;
    }
  }

  retryLoad(): void {
    console.log('Retrying to load student data');
    this.ngZone.run(() => {
      this.ngOnInit(); // Re-run the initialization
    });
  }
}
