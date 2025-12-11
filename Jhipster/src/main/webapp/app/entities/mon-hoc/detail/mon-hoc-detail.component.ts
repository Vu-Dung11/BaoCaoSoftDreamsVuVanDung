import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IMonHoc } from '../mon-hoc.model';

@Component({
  selector: 'jhi-mon-hoc-detail',
  templateUrl: './mon-hoc-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class MonHocDetailComponent {
  monHoc = input<IMonHoc | null>(null);

  previousState(): void {
    window.history.back();
  }
}
