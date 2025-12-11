import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISinhVien, NewSinhVien } from '../sinh-vien.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISinhVien for edit and NewSinhVienFormGroupInput for create.
 */
type SinhVienFormGroupInput = ISinhVien | PartialWithRequiredKeyOf<NewSinhVien>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISinhVien | NewSinhVien> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type SinhVienFormRawValue = FormValueOf<ISinhVien>;

type NewSinhVienFormRawValue = FormValueOf<NewSinhVien>;

type SinhVienFormDefaults = Pick<NewSinhVien, 'id' | 'createdAt' | 'updatedAt'>;

type SinhVienFormGroupContent = {
  id: FormControl<SinhVienFormRawValue['id'] | NewSinhVien['id']>;
  tenSv: FormControl<SinhVienFormRawValue['tenSv']>;
  gioiTinh: FormControl<SinhVienFormRawValue['gioiTinh']>;
  ngaySinh: FormControl<SinhVienFormRawValue['ngaySinh']>;
  lop: FormControl<SinhVienFormRawValue['lop']>;
  khoa: FormControl<SinhVienFormRawValue['khoa']>;
  createdAt: FormControl<SinhVienFormRawValue['createdAt']>;
  updatedAt: FormControl<SinhVienFormRawValue['updatedAt']>;
};

export type SinhVienFormGroup = FormGroup<SinhVienFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SinhVienFormService {
  createSinhVienFormGroup(sinhVien: SinhVienFormGroupInput = { id: null }): SinhVienFormGroup {
    const sinhVienRawValue = this.convertSinhVienToSinhVienRawValue({
      ...this.getFormDefaults(),
      ...sinhVien,
    });
    return new FormGroup<SinhVienFormGroupContent>({
      id: new FormControl(
        { value: sinhVienRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tenSv: new FormControl(sinhVienRawValue.tenSv, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      gioiTinh: new FormControl(sinhVienRawValue.gioiTinh, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      ngaySinh: new FormControl(sinhVienRawValue.ngaySinh),
      lop: new FormControl(sinhVienRawValue.lop, {
        validators: [Validators.maxLength(30)],
      }),
      khoa: new FormControl(sinhVienRawValue.khoa, {
        validators: [Validators.maxLength(30)],
      }),
      createdAt: new FormControl(sinhVienRawValue.createdAt),
      updatedAt: new FormControl(sinhVienRawValue.updatedAt),
    });
  }

  getSinhVien(form: SinhVienFormGroup): ISinhVien | NewSinhVien {
    return this.convertSinhVienRawValueToSinhVien(form.getRawValue() as SinhVienFormRawValue | NewSinhVienFormRawValue);
  }

  resetForm(form: SinhVienFormGroup, sinhVien: SinhVienFormGroupInput): void {
    const sinhVienRawValue = this.convertSinhVienToSinhVienRawValue({ ...this.getFormDefaults(), ...sinhVien });
    form.reset(
      {
        ...sinhVienRawValue,
        id: { value: sinhVienRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SinhVienFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertSinhVienRawValueToSinhVien(rawSinhVien: SinhVienFormRawValue | NewSinhVienFormRawValue): ISinhVien | NewSinhVien {
    return {
      ...rawSinhVien,
      createdAt: dayjs(rawSinhVien.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawSinhVien.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertSinhVienToSinhVienRawValue(
    sinhVien: ISinhVien | (Partial<NewSinhVien> & SinhVienFormDefaults),
  ): SinhVienFormRawValue | PartialWithRequiredKeyOf<NewSinhVienFormRawValue> {
    return {
      ...sinhVien,
      createdAt: sinhVien.createdAt ? sinhVien.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: sinhVien.updatedAt ? sinhVien.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
