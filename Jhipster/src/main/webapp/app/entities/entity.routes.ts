import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'quanLySinhVienApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'sinh-vien',
    data: { pageTitle: 'quanLySinhVienApp.sinhVien.home.title' },
    loadChildren: () => import('./sinh-vien/sinh-vien.routes'),
  },
  {
    path: 'mon-hoc',
    data: { pageTitle: 'quanLySinhVienApp.monHoc.home.title' },
    loadChildren: () => import('./mon-hoc/mon-hoc.routes'),
  },
  {
    path: 'ket-qua-hoc-tap',
    data: { pageTitle: 'quanLySinhVienApp.ketQuaHocTap.home.title' },
    loadChildren: () => import('./ket-qua-hoc-tap/ket-qua-hoc-tap.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
