import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SinhVien } from '../models/sinh-vien';
import { KetQuaHocTap } from '../models/ket-qua-hoc-tap';
import { KetQuaTruotDo } from '../models/ket-qua-truot-do';
import { CapNhatDiemRequest } from '../models/cap-nhat-diem'; // Import model mới
import { HttpParams } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class SinhVienService {
  private apiUrl = 'http://localhost:8080/api/sinh-vien';

  constructor(private http: HttpClient) { }

  getDanhSach(): Observable<SinhVien[]> {
    return this.http.get<SinhVien[]>(`${this.apiUrl}/tensv`);
  }

  getChiTiet(id: number): Observable<SinhVien> {
    return this.http.get<SinhVien>(`${this.apiUrl}/${id}`);
  }

  getDiemSinhVien(svId: number): Observable<KetQuaHocTap[]> {
    return this.http.get<KetQuaHocTap[]>(`http://localhost:8080/api/ket-qua-hoc-tap/sinh-vien/${svId}`);
  }
  // ... trong class SinhVienService
  getKetQuaTruotDo(svId: number): Observable<KetQuaTruotDo[]> {
    return this.http.get<KetQuaTruotDo[]>(`http://localhost:8080/api/ket-qua-hoc-tap/sinh-vien/truot-do/${svId}`);
  }

  // ... trong class Service
  updateDiem(data: CapNhatDiemRequest): Observable<void> {
    // Vì API trả về 204 No Content nên kiểu trả về là void
    return this.http.put<void>(`http://localhost:8080/api/ket-qua-hoc-tap/update-diem`, data);
  }

  timKiemSinhVien(ten: string): Observable<SinhVien[]> {
    const params = new HttpParams().set('ten', ten);
    return this.http.get<SinhVien[]>(`${this.apiUrl}/timkiem`, { params });
  }

}
