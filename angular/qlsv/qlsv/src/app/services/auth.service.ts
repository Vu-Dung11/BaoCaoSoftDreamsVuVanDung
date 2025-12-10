import { Injectable, Inject, PLATFORM_ID } from '@angular/core'; // 1. Import thêm Inject và PLATFORM_ID
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { isPlatformBrowser } from '@angular/common'; // 2. Import isPlatformBrowser

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  // 3. Inject PLATFORM_ID vào constructor
  constructor(
    private http: HttpClient,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, userData);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/authenticate`, credentials).pipe(
      tap((response: any) => {
        if (response && response.token) {
          this.setToken(response.token);
        }
      })
    );
  }

  // --- CÁC HÀM DƯỚI ĐÂY CẦN SỬA ---

  setToken(token: string): void {
    // 4. Chỉ lưu nếu đang ở trình duyệt
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('auth_token', token);
    }
  }

  getToken(): string | null {
    // 5. Chỉ lấy nếu đang ở trình duyệt
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('auth_token');
    }
    return null; // Trả về null nếu đang chạy trên server
  }

  logout(): void {
    // 6. Chỉ xóa nếu đang ở trình duyệt
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('auth_token');
    }
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      return !!this.getToken();
    }
    return false;
  }
}
