import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { KetQuaTruotDo } from '../ket-qua-truot-do';
import { SinhVienService } from '../entities/sinh-vien/service/sinh-vien.service';

@Component({
  selector: 'jhi-ket-qua-truot-do',
  imports: [CommonModule],
  templateUrl: './ket-qua-truot-do.component.html',
  styleUrl: './ket-qua-truot-do.component.scss'
})
export class KetQuaTruotDoComponent implements OnChanges {
   @Input() sinhVienId: number | null = null;
  danhSachKetQua: KetQuaTruotDo[] = [];

  constructor(private svService: SinhVienService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['sinhVienId'] && this.sinhVienId) {
      this.loadData(this.sinhVienId);
    }
  }

  loadData(id: number) {
    this.svService.getKetQuaTruotDo(id).subscribe({
      next: (data) => this.danhSachKetQua = data,
      error: (err: any) => console.error('Lỗi tải kết quả trượt đỗ:', err)
    });
  }
}
