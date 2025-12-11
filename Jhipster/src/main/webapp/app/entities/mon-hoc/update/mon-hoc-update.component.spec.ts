import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { MonHocService } from '../service/mon-hoc.service';
import { IMonHoc } from '../mon-hoc.model';
import { MonHocFormService } from './mon-hoc-form.service';

import { MonHocUpdateComponent } from './mon-hoc-update.component';

describe('MonHoc Management Update Component', () => {
  let comp: MonHocUpdateComponent;
  let fixture: ComponentFixture<MonHocUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let monHocFormService: MonHocFormService;
  let monHocService: MonHocService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MonHocUpdateComponent],
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
      .overrideTemplate(MonHocUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MonHocUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    monHocFormService = TestBed.inject(MonHocFormService);
    monHocService = TestBed.inject(MonHocService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const monHoc: IMonHoc = { id: 8630 };

      activatedRoute.data = of({ monHoc });
      comp.ngOnInit();

      expect(comp.monHoc).toEqual(monHoc);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMonHoc>>();
      const monHoc = { id: 24990 };
      jest.spyOn(monHocFormService, 'getMonHoc').mockReturnValue(monHoc);
      jest.spyOn(monHocService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monHoc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: monHoc }));
      saveSubject.complete();

      // THEN
      expect(monHocFormService.getMonHoc).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(monHocService.update).toHaveBeenCalledWith(expect.objectContaining(monHoc));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMonHoc>>();
      const monHoc = { id: 24990 };
      jest.spyOn(monHocFormService, 'getMonHoc').mockReturnValue({ id: null });
      jest.spyOn(monHocService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monHoc: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: monHoc }));
      saveSubject.complete();

      // THEN
      expect(monHocFormService.getMonHoc).toHaveBeenCalled();
      expect(monHocService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMonHoc>>();
      const monHoc = { id: 24990 };
      jest.spyOn(monHocService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ monHoc });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(monHocService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
