import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../ket-qua-hoc-tap.test-samples';

import { KetQuaHocTapFormService } from './ket-qua-hoc-tap-form.service';

describe('KetQuaHocTap Form Service', () => {
  let service: KetQuaHocTapFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KetQuaHocTapFormService);
  });

  describe('Service methods', () => {
    describe('createKetQuaHocTapFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createKetQuaHocTapFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diemQuaTrinh: expect.any(Object),
            diemThanhPhan: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            sinhVien: expect.any(Object),
            monHoc: expect.any(Object),
          }),
        );
      });

      it('passing IKetQuaHocTap should create a new form with FormGroup', () => {
        const formGroup = service.createKetQuaHocTapFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diemQuaTrinh: expect.any(Object),
            diemThanhPhan: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            sinhVien: expect.any(Object),
            monHoc: expect.any(Object),
          }),
        );
      });
    });

    describe('getKetQuaHocTap', () => {
      it('should return NewKetQuaHocTap for default KetQuaHocTap initial value', () => {
        const formGroup = service.createKetQuaHocTapFormGroup(sampleWithNewData);

        const ketQuaHocTap = service.getKetQuaHocTap(formGroup) as any;

        expect(ketQuaHocTap).toMatchObject(sampleWithNewData);
      });

      it('should return NewKetQuaHocTap for empty KetQuaHocTap initial value', () => {
        const formGroup = service.createKetQuaHocTapFormGroup();

        const ketQuaHocTap = service.getKetQuaHocTap(formGroup) as any;

        expect(ketQuaHocTap).toMatchObject({});
      });

      it('should return IKetQuaHocTap', () => {
        const formGroup = service.createKetQuaHocTapFormGroup(sampleWithRequiredData);

        const ketQuaHocTap = service.getKetQuaHocTap(formGroup) as any;

        expect(ketQuaHocTap).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IKetQuaHocTap should not enable id FormControl', () => {
        const formGroup = service.createKetQuaHocTapFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewKetQuaHocTap should disable id FormControl', () => {
        const formGroup = service.createKetQuaHocTapFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
