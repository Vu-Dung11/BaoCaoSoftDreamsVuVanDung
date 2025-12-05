import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SinhVien } from '../models/sinh-vien.model';

@Injectable({
  providedIn: 'root'
})
export class SinhVienService {
  // Đường dẫn API của bạn
  private apiUrl = 'http://localhost:8080/api/sinh-vien';

  constructor(private http: HttpClient) { }

  // Hàm lấy danh sách sinh viên
  getSinhViens(): Observable<SinhVien[]> {
    return this.http.get<SinhVien[]>(this.apiUrl);
  }
}
