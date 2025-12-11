import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IKetQuaHocTap } from '../ket-qua-hoc-tap.model';
import { KetQuaHocTapService } from '../service/ket-qua-hoc-tap.service';

const ketQuaHocTapResolve = (route: ActivatedRouteSnapshot): Observable<null | IKetQuaHocTap> => {
  const id = route.params.id;
  if (id) {
    return inject(KetQuaHocTapService)
      .find(id)
      .pipe(
        mergeMap((ketQuaHocTap: HttpResponse<IKetQuaHocTap>) => {
          if (ketQuaHocTap.body) {
            return of(ketQuaHocTap.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default ketQuaHocTapResolve;
