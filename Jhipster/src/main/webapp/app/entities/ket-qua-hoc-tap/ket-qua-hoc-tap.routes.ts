import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import KetQuaHocTapResolve from './route/ket-qua-hoc-tap-routing-resolve.service';

const ketQuaHocTapRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/ket-qua-hoc-tap.component').then(m => m.KetQuaHocTapComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/ket-qua-hoc-tap-detail.component').then(m => m.KetQuaHocTapDetailComponent),
    resolve: {
      ketQuaHocTap: KetQuaHocTapResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/ket-qua-hoc-tap-update.component').then(m => m.KetQuaHocTapUpdateComponent),
    resolve: {
      ketQuaHocTap: KetQuaHocTapResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/ket-qua-hoc-tap-update.component').then(m => m.KetQuaHocTapUpdateComponent),
    resolve: {
      ketQuaHocTap: KetQuaHocTapResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default ketQuaHocTapRoute;
