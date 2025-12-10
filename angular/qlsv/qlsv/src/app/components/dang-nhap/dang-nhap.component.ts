import { AuthService } from './../../services/auth.service';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dang-nhap',
  templateUrl: './dang-nhap.component.html',
  styleUrl: './dang-nhap.component.css'
})
export class DangNhapComponent {
  password: string = '';
  email: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }




  onLogin() {
    const credentials = {
      email: this.email,
      password: this.password
    };

    this.authService.login(credentials).subscribe({
      next: (res) => {
        alert('Đăng nhập thành công!');alert('Đăng nhập thành công!');
        // Chuyển hướng sang trang quản lý chính
        this.router.navigate(['/sinh-vien']);
      },
      error: (error) => {
        alert('Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.');
        console.error('Lỗi đăng nhập:', error);

      }});


  }
}
