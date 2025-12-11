import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMonHoc, NewMonHoc } from '../mon-hoc.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMonHoc for edit and NewMonHocFormGroupInput for create.
 */
type MonHocFormGroupInput = IMonHoc | PartialWithRequiredKeyOf<NewMonHoc>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMonHoc | NewMonHoc> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type MonHocFormRawValue = FormValueOf<IMonHoc>;

type NewMonHocFormRawValue = FormValueOf<NewMonHoc>;

type MonHocFormDefaults = Pick<NewMonHoc, 'id' | 'createdAt' | 'updatedAt'>;

type MonHocFormGroupContent = {
  id: FormControl<MonHocFormRawValue['id'] | NewMonHoc['id']>;
  tenMon: FormControl<MonHocFormRawValue['tenMon']>;
  tyLeDiemQuaTrinh: FormControl<MonHocFormRawValue['tyLeDiemQuaTrinh']>;
  createdAt: FormControl<MonHocFormRawValue['createdAt']>;
  updatedAt: FormControl<MonHocFormRawValue['updatedAt']>;
};

export type MonHocFormGroup = FormGroup<MonHocFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MonHocFormService {
  createMonHocFormGroup(monHoc: MonHocFormGroupInput = { id: null }): MonHocFormGroup {
    const monHocRawValue = this.convertMonHocToMonHocRawValue({
      ...this.getFormDefaults(),
      ...monHoc,
    });
    return new FormGroup<MonHocFormGroupContent>({
      id: new FormControl(
        { value: monHocRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tenMon: new FormControl(monHocRawValue.tenMon, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      tyLeDiemQuaTrinh: new FormControl(monHocRawValue.tyLeDiemQuaTrinh, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(monHocRawValue.createdAt),
      updatedAt: new FormControl(monHocRawValue.updatedAt),
    });
  }

  getMonHoc(form: MonHocFormGroup): IMonHoc | NewMonHoc {
    return this.convertMonHocRawValueToMonHoc(form.getRawValue() as MonHocFormRawValue | NewMonHocFormRawValue);
  }

  resetForm(form: MonHocFormGroup, monHoc: MonHocFormGroupInput): void {
    const monHocRawValue = this.convertMonHocToMonHocRawValue({ ...this.getFormDefaults(), ...monHoc });
    form.reset(
      {
        ...monHocRawValue,
        id: { value: monHocRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MonHocFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertMonHocRawValueToMonHoc(rawMonHoc: MonHocFormRawValue | NewMonHocFormRawValue): IMonHoc | NewMonHoc {
    return {
      ...rawMonHoc,
      createdAt: dayjs(rawMonHoc.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawMonHoc.updatedAt, DATE_TIME_FORMAT),
    };
  }

  private convertMonHocToMonHocRawValue(
    monHoc: IMonHoc | (Partial<NewMonHoc> & MonHocFormDefaults),
  ): MonHocFormRawValue | PartialWithRequiredKeyOf<NewMonHocFormRawValue> {
    return {
      ...monHoc,
      createdAt: monHoc.createdAt ? monHoc.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: monHoc.updatedAt ? monHoc.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
