import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MonHocResolve from './route/mon-hoc-routing-resolve.service';

const monHocRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/mon-hoc.component').then(m => m.MonHocComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/mon-hoc-detail.component').then(m => m.MonHocDetailComponent),
    resolve: {
      monHoc: MonHocResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/mon-hoc-update.component').then(m => m.MonHocUpdateComponent),
    resolve: {
      monHoc: MonHocResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/mon-hoc-update.component').then(m => m.MonHocUpdateComponent),
    resolve: {
      monHoc: MonHocResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default monHocRoute;
