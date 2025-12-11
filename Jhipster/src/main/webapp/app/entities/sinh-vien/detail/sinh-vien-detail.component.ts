import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ISinhVien } from '../sinh-vien.model';

@Component({
  selector: 'jhi-sinh-vien-detail',
  templateUrl: './sinh-vien-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SinhVienDetailComponent {
  sinhVien = input<ISinhVien | null>(null);

  previousState(): void {
    window.history.back();
  }
}
