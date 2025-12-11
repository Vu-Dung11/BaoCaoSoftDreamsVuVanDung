import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../sinh-vien.test-samples';

import { SinhVienFormService } from './sinh-vien-form.service';

describe('SinhVien Form Service', () => {
  let service: SinhVienFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SinhVienFormService);
  });

  describe('Service methods', () => {
    describe('createSinhVienFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSinhVienFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenSv: expect.any(Object),
            gioiTinh: expect.any(Object),
            ngaySinh: expect.any(Object),
            lop: expect.any(Object),
            khoa: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });

      it('passing ISinhVien should create a new form with FormGroup', () => {
        const formGroup = service.createSinhVienFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenSv: expect.any(Object),
            gioiTinh: expect.any(Object),
            ngaySinh: expect.any(Object),
            lop: expect.any(Object),
            khoa: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });
    });

    describe('getSinhVien', () => {
      it('should return NewSinhVien for default SinhVien initial value', () => {
        const formGroup = service.createSinhVienFormGroup(sampleWithNewData);

        const sinhVien = service.getSinhVien(formGroup) as any;

        expect(sinhVien).toMatchObject(sampleWithNewData);
      });

      it('should return NewSinhVien for empty SinhVien initial value', () => {
        const formGroup = service.createSinhVienFormGroup();

        const sinhVien = service.getSinhVien(formGroup) as any;

        expect(sinhVien).toMatchObject({});
      });

      it('should return ISinhVien', () => {
        const formGroup = service.createSinhVienFormGroup(sampleWithRequiredData);

        const sinhVien = service.getSinhVien(formGroup) as any;

        expect(sinhVien).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISinhVien should not enable id FormControl', () => {
        const formGroup = service.createSinhVienFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSinhVien should disable id FormControl', () => {
        const formGroup = service.createSinhVienFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
