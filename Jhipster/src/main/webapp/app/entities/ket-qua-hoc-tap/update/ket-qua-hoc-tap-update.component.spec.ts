import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ISinhVien } from 'app/entities/sinh-vien/sinh-vien.model';
import { SinhVienService } from 'app/entities/sinh-vien/service/sinh-vien.service';
import { IMonHoc } from 'app/entities/mon-hoc/mon-hoc.model';
import { MonHocService } from 'app/entities/mon-hoc/service/mon-hoc.service';
import { IKetQuaHocTap } from '../ket-qua-hoc-tap.model';
import { KetQuaHocTapService } from '../service/ket-qua-hoc-tap.service';
import { KetQuaHocTapFormService } from './ket-qua-hoc-tap-form.service';

import { KetQuaHocTapUpdateComponent } from './ket-qua-hoc-tap-update.component';

describe('KetQuaHocTap Management Update Component', () => {
  let comp: KetQuaHocTapUpdateComponent;
  let fixture: ComponentFixture<KetQuaHocTapUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ketQuaHocTapFormService: KetQuaHocTapFormService;
  let ketQuaHocTapService: KetQuaHocTapService;
  let sinhVienService: SinhVienService;
  let monHocService: MonHocService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [KetQuaHocTapUpdateComponent],
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
      .overrideTemplate(KetQuaHocTapUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(KetQuaHocTapUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ketQuaHocTapFormService = TestBed.inject(KetQuaHocTapFormService);
    ketQuaHocTapService = TestBed.inject(KetQuaHocTapService);
    sinhVienService = TestBed.inject(SinhVienService);
    monHocService = TestBed.inject(MonHocService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call SinhVien query and add missing value', () => {
      const ketQuaHocTap: IKetQuaHocTap = { id: 15899 };
      const sinhVien: ISinhVien = { id: 28148 };
      ketQuaHocTap.sinhVien = sinhVien;

      const sinhVienCollection: ISinhVien[] = [{ id: 28148 }];
      jest.spyOn(sinhVienService, 'query').mockReturnValue(of(new HttpResponse({ body: sinhVienCollection })));
      const additionalSinhViens = [sinhVien];
      const expectedCollection: ISinhVien[] = [...additionalSinhViens, ...sinhVienCollection];
      jest.spyOn(sinhVienService, 'addSinhVienToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ketQuaHocTap });
      comp.ngOnInit();

      expect(sinhVienService.query).toHaveBeenCalled();
      expect(sinhVienService.addSinhVienToCollectionIfMissing).toHaveBeenCalledWith(
        sinhVienCollection,
        ...additionalSinhViens.map(expect.objectContaining),
      );
      expect(comp.sinhViensSharedCollection).toEqual(expectedCollection);
    });

    it('should call MonHoc query and add missing value', () => {
      const ketQuaHocTap: IKetQuaHocTap = { id: 15899 };
      const monHoc: IMonHoc = { id: 24990 };
      ketQuaHocTap.monHoc = monHoc;

      const monHocCollection: IMonHoc[] = [{ id: 24990 }];
      jest.spyOn(monHocService, 'query').mockReturnValue(of(new HttpResponse({ body: monHocCollection })));
      const additionalMonHocs = [monHoc];
      const expectedCollection: IMonHoc[] = [...additionalMonHocs, ...monHocCollection];
      jest.spyOn(monHocService, 'addMonHocToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ketQuaHocTap });
      comp.ngOnInit();

      expect(monHocService.query).toHaveBeenCalled();
      expect(monHocService.addMonHocToCollectionIfMissing).toHaveBeenCalledWith(
        monHocCollection,
        ...additionalMonHocs.map(expect.objectContaining),
      );
      expect(comp.monHocsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const ketQuaHocTap: IKetQuaHocTap = { id: 15899 };
      const sinhVien: ISinhVien = { id: 28148 };
      ketQuaHocTap.sinhVien = sinhVien;
      const monHoc: IMonHoc = { id: 24990 };
      ketQuaHocTap.monHoc = monHoc;

      activatedRoute.data = of({ ketQuaHocTap });
      comp.ngOnInit();

      expect(comp.sinhViensSharedCollection).toContainEqual(sinhVien);
      expect(comp.monHocsSharedCollection).toContainEqual(monHoc);
      expect(comp.ketQuaHocTap).toEqual(ketQuaHocTap);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKetQuaHocTap>>();
      const ketQuaHocTap = { id: 7126 };
      jest.spyOn(ketQuaHocTapFormService, 'getKetQuaHocTap').mockReturnValue(ketQuaHocTap);
      jest.spyOn(ketQuaHocTapService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ketQuaHocTap });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ketQuaHocTap }));
      saveSubject.complete();

      // THEN
      expect(ketQuaHocTapFormService.getKetQuaHocTap).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ketQuaHocTapService.update).toHaveBeenCalledWith(expect.objectContaining(ketQuaHocTap));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKetQuaHocTap>>();
      const ketQuaHocTap = { id: 7126 };
      jest.spyOn(ketQuaHocTapFormService, 'getKetQuaHocTap').mockReturnValue({ id: null });
      jest.spyOn(ketQuaHocTapService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ketQuaHocTap: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ketQuaHocTap }));
      saveSubject.complete();

      // THEN
      expect(ketQuaHocTapFormService.getKetQuaHocTap).toHaveBeenCalled();
      expect(ketQuaHocTapService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IKetQuaHocTap>>();
      const ketQuaHocTap = { id: 7126 };
      jest.spyOn(ketQuaHocTapService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ketQuaHocTap });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ketQuaHocTapService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSinhVien', () => {
      it('should forward to sinhVienService', () => {
        const entity = { id: 28148 };
        const entity2 = { id: 21153 };
        jest.spyOn(sinhVienService, 'compareSinhVien');
        comp.compareSinhVien(entity, entity2);
        expect(sinhVienService.compareSinhVien).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMonHoc', () => {
      it('should forward to monHocService', () => {
        const entity = { id: 24990 };
        const entity2 = { id: 8630 };
        jest.spyOn(monHocService, 'compareMonHoc');
        comp.compareMonHoc(entity, entity2);
        expect(monHocService.compareMonHoc).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
