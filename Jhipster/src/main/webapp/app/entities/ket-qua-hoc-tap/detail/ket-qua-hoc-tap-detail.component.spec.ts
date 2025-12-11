import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { KetQuaHocTapDetailComponent } from './ket-qua-hoc-tap-detail.component';

describe('KetQuaHocTap Management Detail Component', () => {
  let comp: KetQuaHocTapDetailComponent;
  let fixture: ComponentFixture<KetQuaHocTapDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KetQuaHocTapDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./ket-qua-hoc-tap-detail.component').then(m => m.KetQuaHocTapDetailComponent),
              resolve: { ketQuaHocTap: () => of({ id: 7126 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(KetQuaHocTapDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(KetQuaHocTapDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load ketQuaHocTap on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', KetQuaHocTapDetailComponent);

      // THEN
      expect(instance.ketQuaHocTap()).toEqual(expect.objectContaining({ id: 7126 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
