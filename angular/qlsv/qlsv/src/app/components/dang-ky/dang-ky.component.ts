import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dang-ky',
  templateUrl: './dang-ky.component.html',
  styleUrl: './dang-ky.component.css'
})
export class DangKyComponent {
  password: string = '';
  email: string = '';
  firstname: string = '';
  lastname: string = '';

  constructor(
    private authenService: AuthService,
    private router: Router
  ) {
  }

  onRegister() {
    const userData = {
      email: this.email,
      password: this.password,
      firstName: this.firstname,
      lastName: this.lastname
    };
    this.authenService.register(userData).subscribe({
      next: (res) => {
        alert('Đăng ký thành công! Vui lòng đăng nhập.');
        // Chuyển hướng sang trang đăng nhập
        this.router.navigate(['/dang-nhap']);
      },
      error: (error) => {
        alert('Đăng ký thất bại. Vui lòng thử lại.');
        console.error('Lỗi đăng ký:', error);
      }
    });



  }

}



