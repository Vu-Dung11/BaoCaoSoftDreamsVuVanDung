import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IKetQuaHocTap } from '../ket-qua-hoc-tap.model';
import { KetQuaHocTapService } from '../service/ket-qua-hoc-tap.service';

@Component({
  templateUrl: './ket-qua-hoc-tap-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class KetQuaHocTapDeleteDialogComponent {
  ketQuaHocTap?: IKetQuaHocTap;

  protected ketQuaHocTapService = inject(KetQuaHocTapService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ketQuaHocTapService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
