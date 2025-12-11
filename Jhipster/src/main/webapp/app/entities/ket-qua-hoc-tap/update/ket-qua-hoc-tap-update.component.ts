import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISinhVien } from 'app/entities/sinh-vien/sinh-vien.model';
import { SinhVienService } from 'app/entities/sinh-vien/service/sinh-vien.service';
import { IMonHoc } from 'app/entities/mon-hoc/mon-hoc.model';
import { MonHocService } from 'app/entities/mon-hoc/service/mon-hoc.service';
import { KetQuaHocTapService } from '../service/ket-qua-hoc-tap.service';
import { IKetQuaHocTap } from '../ket-qua-hoc-tap.model';
import { KetQuaHocTapFormGroup, KetQuaHocTapFormService } from './ket-qua-hoc-tap-form.service';

@Component({
  selector: 'jhi-ket-qua-hoc-tap-update',
  templateUrl: './ket-qua-hoc-tap-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class KetQuaHocTapUpdateComponent implements OnInit {
  isSaving = false;
  ketQuaHocTap: IKetQuaHocTap | null = null;

  sinhViensSharedCollection: ISinhVien[] = [];
  monHocsSharedCollection: IMonHoc[] = [];

  protected ketQuaHocTapService = inject(KetQuaHocTapService);
  protected ketQuaHocTapFormService = inject(KetQuaHocTapFormService);
  protected sinhVienService = inject(SinhVienService);
  protected monHocService = inject(MonHocService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: KetQuaHocTapFormGroup = this.ketQuaHocTapFormService.createKetQuaHocTapFormGroup();

  compareSinhVien = (o1: ISinhVien | null, o2: ISinhVien | null): boolean => this.sinhVienService.compareSinhVien(o1, o2);

  compareMonHoc = (o1: IMonHoc | null, o2: IMonHoc | null): boolean => this.monHocService.compareMonHoc(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ketQuaHocTap }) => {
      this.ketQuaHocTap = ketQuaHocTap;
      if (ketQuaHocTap) {
        this.updateForm(ketQuaHocTap);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ketQuaHocTap = this.ketQuaHocTapFormService.getKetQuaHocTap(this.editForm);
    if (ketQuaHocTap.id !== null) {
      this.subscribeToSaveResponse(this.ketQuaHocTapService.update(ketQuaHocTap));
    } else {
      this.subscribeToSaveResponse(this.ketQuaHocTapService.create(ketQuaHocTap));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKetQuaHocTap>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(ketQuaHocTap: IKetQuaHocTap): void {
    this.ketQuaHocTap = ketQuaHocTap;
    this.ketQuaHocTapFormService.resetForm(this.editForm, ketQuaHocTap);

    this.sinhViensSharedCollection = this.sinhVienService.addSinhVienToCollectionIfMissing<ISinhVien>(
      this.sinhViensSharedCollection,
      ketQuaHocTap.sinhVien,
    );
    this.monHocsSharedCollection = this.monHocService.addMonHocToCollectionIfMissing<IMonHoc>(
      this.monHocsSharedCollection,
      ketQuaHocTap.monHoc,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.sinhVienService
      .query()
      .pipe(map((res: HttpResponse<ISinhVien[]>) => res.body ?? []))
      .pipe(
        map((sinhViens: ISinhVien[]) =>
          this.sinhVienService.addSinhVienToCollectionIfMissing<ISinhVien>(sinhViens, this.ketQuaHocTap?.sinhVien),
        ),
      )
      .subscribe((sinhViens: ISinhVien[]) => (this.sinhViensSharedCollection = sinhViens));

    this.monHocService
      .query()
      .pipe(map((res: HttpResponse<IMonHoc[]>) => res.body ?? []))
      .pipe(map((monHocs: IMonHoc[]) => this.monHocService.addMonHocToCollectionIfMissing<IMonHoc>(monHocs, this.ketQuaHocTap?.monHoc)))
      .subscribe((monHocs: IMonHoc[]) => (this.monHocsSharedCollection = monHocs));
  }
}
