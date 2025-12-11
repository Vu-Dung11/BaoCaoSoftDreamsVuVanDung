import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISinhVien } from '../sinh-vien.model';
import { SinhVienService } from '../service/sinh-vien.service';

const sinhVienResolve = (route: ActivatedRouteSnapshot): Observable<null | ISinhVien> => {
  const id = route.params.id;
  if (id) {
    return inject(SinhVienService)
      .find(id)
      .pipe(
        mergeMap((sinhVien: HttpResponse<ISinhVien>) => {
          if (sinhVien.body) {
            return of(sinhVien.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default sinhVienResolve;
