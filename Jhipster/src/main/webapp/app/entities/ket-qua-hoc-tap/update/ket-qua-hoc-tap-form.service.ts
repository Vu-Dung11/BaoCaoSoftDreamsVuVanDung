import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IKetQuaHocTap, NewKetQuaHocTap } from '../ket-qua-hoc-tap.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IKetQuaHocTap for edit and NewKetQuaHocTapFormGroupInput for create.
 */
type KetQuaHocTapFormGroupInput = IKetQuaHocTap | PartialWithRequiredKeyOf<NewKetQuaHocTap>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IKetQuaHocTap | NewKetQuaHocTap> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type KetQuaHocTapFormRawValue = FormValueOf<IKetQuaHocTap>;

type NewKetQuaHocTapFormRawValue = FormValueOf<NewKetQuaHocTap>;

type KetQuaHocTapFormDefaults = Pick<NewKetQuaHocTap, 'id' | 'createdAt' | 'updatedAt'>;

type KetQuaHocTapFormGroupContent = {
  id: FormControl<KetQuaHocTapFormRawValue['id'] | NewKetQuaHocTap['id']>;
  diemQuaTrinh: FormControl<KetQuaHocTapFormRawValue['diemQuaTrinh']>;
  diemThanhPhan: FormControl<KetQuaHocTapFormRawValue['diemThanhPhan']>;
  createdAt: FormControl<KetQuaHocTapFormRawValue['createdAt']>;
  updatedAt: FormControl<KetQuaHocTapFormRawValue['updatedAt']>;
  sinhVien: FormControl<KetQuaHocTapFormRawValue['sinhVien']>;
  monHoc: FormControl<KetQuaHocTapFormRawValue['monHoc']>;
};

export type KetQuaHocTapFormGroup = FormGroup<KetQuaHocTapFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class KetQuaHocTapFormService {
  createKetQuaHocTapFormGroup(ketQuaHocTap: KetQuaHocTapFormGroupInput = { id: null }): KetQuaHocTapFormGroup {
    const ketQuaHocTapRawValue = this.convertKetQuaHocTapToKetQuaHocTapRawValue({
      ...this.getFormDefaults(),
      ...ketQuaHocTap,
    });
    return new FormGroup<KetQuaHocTapFormGroupContent>({
      id: new FormControl(
        { value: ketQuaHocTapRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      diemQuaTrinh: new FormControl(ketQuaHocTapRawValue.diemQuaTrinh),
      diemThanhPhan: new FormControl(ketQuaHocTapRawValue.diemThanhPhan),
      createdAt: new FormControl(ketQuaHocTapRawValue.createdAt),
      updatedAt: new FormControl(ketQuaHocTapRawValue.updatedAt),
      sinhVien: new FormControl(ketQuaHocTapRawValue.sinhVien, {
        validators: [Validators.required],
      }),
      monHoc: new FormControl(ketQuaHocTapRawValue.monHoc, {
        validators: [Validators.required],
      }),
    });
  }

  getKetQuaHocTap(form: KetQuaHocTapFormGroup): IKetQuaHocTap | NewKetQuaHocTap {
    return this.convertKetQuaHocTapRawValueToKetQuaHocTap(form.getRawValue() as KetQuaHocTapFormRawValue | NewKetQuaHocTapFormRawValue);
  }

  resetForm(form: KetQuaHocTapFormGroup, ketQuaHocTap: KetQuaHocTapFormGroupInput): void {
    const ketQuaHocTapRawValue = this.convertKetQuaHocTapToKetQuaHocTapRawValue({ ...this.getFormDefaults(), ...ketQuaHocTap });
    form.reset(
      {
        ...ketQuaHocTapRawValue,
        id: { value: ketQuaHocTapRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): KetQuaHocTapFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertKetQuaHocTapRawValueToKetQuaHocTap(
    rawKetQuaHocTap: KetQuaHocTapFormRawValue | NewKetQuaHocTapFormRawValue,
  ): IKetQuaHocTap | NewKetQuaHocTap {
    return {
      ...rawKetQuaHocTap,
      createdAt: dayjs(rawKetQuaHocTap.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawKetQuaHocTap.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertKetQuaHocTapToKetQuaHocTapRawValue(
    ketQuaHocTap: IKetQuaHocTap | (Partial<NewKetQuaHocTap> & KetQuaHocTapFormDefaults),
  ): KetQuaHocTapFormRawValue | PartialWithRequiredKeyOf<NewKetQuaHocTapFormRawValue> {
    return {
      ...ketQuaHocTap,
      createdAt: ketQuaHocTap.createdAt ? ketQuaHocTap.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: ketQuaHocTap.updatedAt ? ketQuaHocTap.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
