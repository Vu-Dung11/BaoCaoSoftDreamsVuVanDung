import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMonHoc } from '../mon-hoc.model';
import { MonHocService } from '../service/mon-hoc.service';
import { MonHocFormGroup, MonHocFormService } from './mon-hoc-form.service';

@Component({
  selector: 'jhi-mon-hoc-update',
  templateUrl: './mon-hoc-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MonHocUpdateComponent implements OnInit {
  isSaving = false;
  monHoc: IMonHoc | null = null;

  protected monHocService = inject(MonHocService);
  protected monHocFormService = inject(MonHocFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MonHocFormGroup = this.monHocFormService.createMonHocFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ monHoc }) => {
      this.monHoc = monHoc;
      if (monHoc) {
        this.updateForm(monHoc);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const monHoc = this.monHocFormService.getMonHoc(this.editForm);
    if (monHoc.id !== null) {
      this.subscribeToSaveResponse(this.monHocService.update(monHoc));
    } else {
      this.subscribeToSaveResponse(this.monHocService.create(monHoc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonHoc>>): void {
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

  protected updateForm(monHoc: IMonHoc): void {
    this.monHoc = monHoc;
    this.monHocFormService.resetForm(this.editForm, monHoc);
  }
}
