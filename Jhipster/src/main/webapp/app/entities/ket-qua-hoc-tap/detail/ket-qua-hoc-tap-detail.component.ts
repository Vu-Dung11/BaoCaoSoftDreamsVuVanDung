import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IKetQuaHocTap } from '../ket-qua-hoc-tap.model';

@Component({
  selector: 'jhi-ket-qua-hoc-tap-detail',
  templateUrl: './ket-qua-hoc-tap-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class KetQuaHocTapDetailComponent {
  ketQuaHocTap = input<IKetQuaHocTap | null>(null);

  previousState(): void {
    window.history.back();
  }
}
