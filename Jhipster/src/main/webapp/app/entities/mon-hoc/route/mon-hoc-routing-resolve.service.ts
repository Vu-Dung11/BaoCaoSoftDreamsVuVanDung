import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMonHoc } from '../mon-hoc.model';
import { MonHocService } from '../service/mon-hoc.service';

const monHocResolve = (route: ActivatedRouteSnapshot): Observable<null | IMonHoc> => {
  const id = route.params.id;
  if (id) {
    return inject(MonHocService)
      .find(id)
      .pipe(
        mergeMap((monHoc: HttpResponse<IMonHoc>) => {
          if (monHoc.body) {
            return of(monHoc.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default monHocResolve;
