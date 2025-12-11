import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SinhVienResolve from './route/sinh-vien-routing-resolve.service';

const sinhVienRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sinh-vien.component').then(m => m.SinhVienComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sinh-vien-detail.component').then(m => m.SinhVienDetailComponent),
    resolve: {
      sinhVien: SinhVienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sinh-vien-update.component').then(m => m.SinhVienUpdateComponent),
    resolve: {
      sinhVien: SinhVienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sinh-vien-update.component').then(m => m.SinhVienUpdateComponent),
    resolve: {
      sinhVien: SinhVienResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sinhVienRoute;
