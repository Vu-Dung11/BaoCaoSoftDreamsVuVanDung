import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(
    private authenService: AuthService,
    private router: Router
  ) { }

  onLogout() {
    this.authenService.logout();
    this.router.navigate(['']);
  }



  // logout(): void {
  //   if (isPlatformBrowser(this.platformId)) {
  //     // 1. Xóa token
  //     localStorage.removeItem('auth_token');

  //     // 2. (Tùy chọn) Xóa thêm thông tin user nếu có
  //     localStorage.removeItem('user_info');
  //   }

  //   // 3. Đá về trang login
  //   this.router.navigate(['/login']);
  // }

}
