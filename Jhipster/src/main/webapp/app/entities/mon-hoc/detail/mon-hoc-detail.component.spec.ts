import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MonHocDetailComponent } from './mon-hoc-detail.component';

describe('MonHoc Management Detail Component', () => {
  let comp: MonHocDetailComponent;
  let fixture: ComponentFixture<MonHocDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MonHocDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./mon-hoc-detail.component').then(m => m.MonHocDetailComponent),
              resolve: { monHoc: () => of({ id: 24990 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MonHocDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonHocDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load monHoc on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MonHocDetailComponent);

      // THEN
      expect(instance.monHoc()).toEqual(expect.objectContaining({ id: 24990 }));
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
