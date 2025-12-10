import { Component, Input, OnChanges, SimpleChanges, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SinhVienService } from '../../services/sinh-vien.service';
import { KetQuaHocTap } from '../../models/ket-qua-hoc-tap';
import { CapNhatDiemRequest } from '../../models/cap-nhat-diem';
import { NgZone } from '@angular/core';

@Component({
  selector: 'app-ket-qua-hoc-tap',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './ket-qua-hoc-tap.component.html',
  styleUrls: ['./ket-qua-hoc-tap.component.css']
})
export class KetQuaHocTapComponent implements OnChanges {
  @Input() sinhVienId: number | null = null;
  bangDiem: KetQuaHocTap[] = [];

  // Biến cho chức năng sửa
  isEditing: boolean = true;
  editingData: CapNhatDiemRequest = {
    maSv: 0,
    maMon: 0,
    diemQuaTrinh: 0,
    diemThanhPhan: 0
  };
  tenMonDangSua: string = '';

  constructor(
    private svService: SinhVienService,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef
  ) {
    this.isEditing = false
    console.log('=== KET QUA HOC TAP COMPONENT CONSTRUCTOR ===');
    console.log('Component initialized');
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['sinhVienId'] && this.sinhVienId) {
      console.log('Calling layBangDiem with ID:', this.sinhVienId);
      this.layBangDiem(this.sinhVienId);
    } else {
      console.log('No sinhVienId or no changes detected');
    }
  }

  layBangDiem(id: number) {

    this.svService.getDiemSinhVien(id).subscribe({
      next: (data) => {
        this.bangDiem = data;
      },
      error: (err: any) => {

        this.bangDiem = [];
      }
    });
  }

  // 1. Hàm mở form sửa
  openEditForm(item: KetQuaHocTap) {
    this.isEditing = true;

    this.ngZone.run(() => {
      this.tenMonDangSua = item.monHocTen;
      this.editingData = {
        maSv: item.sinhVienId || 0, // Mapping ID từ dữ liệu lấy về
        maMon: item.monHocId || 0,
        diemQuaTrinh: item.diem_qua_trinh,
        diemThanhPhan: item.diem_thanh_phan
      };
      this.cdr.detectChanges();
      console.log('Change detection triggered');
    });
  }


  // 2. Hàm đóng form
  closeEditForm() {
    this.isEditing = false;
  }

  // onEditClick(diem: any) {
  //   this.openEditForm(diem);
  // }

  // 3. Hàm lưu dữ liệu
  saveDiem() {


    if (this.editingData.diemQuaTrinh < 0 || this.editingData.diemQuaTrinh > 10 ||
      this.editingData.diemThanhPhan < 0 || this.editingData.diemThanhPhan > 10) {
      alert('Điểm không hợp lệ! Phải từ 0 đến 10.');
      return;
  }

    if (!confirm('Bạn có chắc chắn muốn cập nhật điểm?')) return;

    this.svService.updateDiem(this.editingData).subscribe({
      next: () => {
        alert('Cập nhật thành công!');
        this.isEditing = false;
        // Tải lại bảng điểm để thấy dữ liệu mới
        if (this.sinhVienId) this.layBangDiem(this.sinhVienId);
      },
      error: (err: any) => {
        console.error(err);
        alert('Cập nhật thất bại!');
      }
    });
  }
}
