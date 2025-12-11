import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISinhVien } from '../sinh-vien.model';
import { SinhVienService } from '../service/sinh-vien.service';
import { SinhVienFormGroup, SinhVienFormService } from './sinh-vien-form.service';

@Component({
  selector: 'jhi-sinh-vien-update',
  templateUrl: './sinh-vien-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SinhVienUpdateComponent implements OnInit {
  isSaving = false;
  sinhVien: ISinhVien | null = null;

  protected sinhVienService = inject(SinhVienService);
  protected sinhVienFormService = inject(SinhVienFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SinhVienFormGroup = this.sinhVienFormService.createSinhVienFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sinhVien }) => {
      this.sinhVien = sinhVien;
      if (sinhVien) {
        this.updateForm(sinhVien);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sinhVien = this.sinhVienFormService.getSinhVien(this.editForm);
    if (sinhVien.id !== null) {
      this.subscribeToSaveResponse(this.sinhVienService.update(sinhVien));
    } else {
      this.subscribeToSaveResponse(this.sinhVienService.create(sinhVien));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISinhVien>>): void {
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

  protected updateForm(sinhVien: ISinhVien): void {
    this.sinhVien = sinhVien;
    this.sinhVienFormService.resetForm(this.editForm, sinhVien);
  }
}
