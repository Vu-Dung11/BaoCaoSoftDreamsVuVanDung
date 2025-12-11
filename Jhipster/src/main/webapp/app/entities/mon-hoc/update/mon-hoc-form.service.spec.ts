import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../mon-hoc.test-samples';

import { MonHocFormService } from './mon-hoc-form.service';

describe('MonHoc Form Service', () => {
  let service: MonHocFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MonHocFormService);
  });

  describe('Service methods', () => {
    describe('createMonHocFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMonHocFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenMon: expect.any(Object),
            tyLeDiemQuaTrinh: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });

      it('passing IMonHoc should create a new form with FormGroup', () => {
        const formGroup = service.createMonHocFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tenMon: expect.any(Object),
            tyLeDiemQuaTrinh: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });
    });

    describe('getMonHoc', () => {
      it('should return NewMonHoc for default MonHoc initial value', () => {
        const formGroup = service.createMonHocFormGroup(sampleWithNewData);

        const monHoc = service.getMonHoc(formGroup) as any;

        expect(monHoc).toMatchObject(sampleWithNewData);
      });

      it('should return NewMonHoc for empty MonHoc initial value', () => {
        const formGroup = service.createMonHocFormGroup();

        const monHoc = service.getMonHoc(formGroup) as any;

        expect(monHoc).toMatchObject({});
      });

      it('should return IMonHoc', () => {
        const formGroup = service.createMonHocFormGroup(sampleWithRequiredData);

        const monHoc = service.getMonHoc(formGroup) as any;

        expect(monHoc).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMonHoc should not enable id FormControl', () => {
        const formGroup = service.createMonHocFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMonHoc should disable id FormControl', () => {
        const formGroup = service.createMonHocFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
