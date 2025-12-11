import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SinhVienService } from '../service/sinh-vien.service';
import { ISinhVien } from '../sinh-vien.model';
import { SinhVienFormService } from './sinh-vien-form.service';

import { SinhVienUpdateComponent } from './sinh-vien-update.component';

describe('SinhVien Management Update Component', () => {
  let comp: SinhVienUpdateComponent;
  let fixture: ComponentFixture<SinhVienUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let sinhVienFormService: SinhVienFormService;
  let sinhVienService: SinhVienService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SinhVienUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SinhVienUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SinhVienUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    sinhVienFormService = TestBed.inject(SinhVienFormService);
    sinhVienService = TestBed.inject(SinhVienService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const sinhVien: ISinhVien = { id: 21153 };

      activatedRoute.data = of({ sinhVien });
      comp.ngOnInit();

      expect(comp.sinhVien).toEqual(sinhVien);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISinhVien>>();
      const sinhVien = { id: 28148 };
      jest.spyOn(sinhVienFormService, 'getSinhVien').mockReturnValue(sinhVien);
      jest.spyOn(sinhVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sinhVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sinhVien }));
      saveSubject.complete();

      // THEN
      expect(sinhVienFormService.getSinhVien).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(sinhVienService.update).toHaveBeenCalledWith(expect.objectContaining(sinhVien));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISinhVien>>();
      const sinhVien = { id: 28148 };
      jest.spyOn(sinhVienFormService, 'getSinhVien').mockReturnValue({ id: null });
      jest.spyOn(sinhVienService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sinhVien: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: sinhVien }));
      saveSubject.complete();

      // THEN
      expect(sinhVienFormService.getSinhVien).toHaveBeenCalled();
      expect(sinhVienService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISinhVien>>();
      const sinhVien = { id: 28148 };
      jest.spyOn(sinhVienService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ sinhVien });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(sinhVienService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
