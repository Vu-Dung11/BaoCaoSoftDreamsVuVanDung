import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SinhVienService } from '../../services/sinh-vien.service';
import { KetQuaTruotDo } from '../../models/ket-qua-truot-do';

@Component({
  selector: 'app-ket-qua-truot-do',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ket-qua-truot-do.component.html',
  styleUrls: ['./ket-qua-truot-do.component.css']
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
