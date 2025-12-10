import { HomeComponent } from './components/home/home.component';

import { Routes } from '@angular/router';
// Import 2 component bạn vừa tạo
import { DanhSachSinhVienComponent } from './components/danh-sach-sinh-vien/danh-sach-sinh-vien.component';
import { ChiTietSinhVienComponent } from './components/chi-tiet-sinh-vien/chi-tiet-sinh-vien.component';
import { DangNhapComponent } from './components/dang-nhap/dang-nhap.component';
import { DangKyComponent } from './components/dang-ky/dang-ky.component';



export const routes: Routes = [
  // Nếu người dùng vào trang chủ (trống), tự động chuyển hướng sang danh sách


  { path: '', component: HomeComponent },  // Thêm route cho home
  { path: 'sinh-vien', component: DanhSachSinhVienComponent },
  { path: 'sinh-vien/:id', component: ChiTietSinhVienComponent },
  { path: 'dang-nhap', component: DangNhapComponent },
  { path: 'dang-ky', component: DangKyComponent },  // Thêm route cho đăng ký
  { path: 'test', component: DanhSachSinhVienComponent },
  { path: '**', redirectTo: '' }  // Wildcard route




];
