import { Component } from '@angular/core';

// Định nghĩa Interface cho một Mục Menu để dễ quản lý dữ liệu
interface MenuItem {
  icon: string; // Tên icon từ Angular Material Icons
  label: string; // Tên hiển thị trên Sidebar
  route: string; // Đường dẫn Angular Router
  roles: string[]; // Các vai trò được phép truy cập (Phân quyền)
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  // Giả định vai trò người dùng hiện tại (sẽ lấy từ Service Auth thực tế)
  currentUserRoles: string[] = ['Admin', 'Faculty'];

  // Cấu trúc menu được định nghĩa dựa trên yêu cầu của bạn
  menuItems: MenuItem[] = [
    // === NHÓM QUẢN LÝ (CHỦ YẾU DÀNH CHO ADMIN) ===
    {
      icon: 'people',
      label: 'Danh sách Sinh viên',
      route: '/admin/students',
      roles: ['Admin'] // Mục 1: Xem danh sách sinh viên
    },
    {
      icon: 'book',
      label: 'Quản lý Khóa học',
      route: '/admin/courses',
      roles: ['Admin']
    },

    // === NHÓM KẾT QUẢ VÀ ĐÁNH GIÁ (CHỦ YẾU DÀNH CHO GIÁO VIÊN/ADMIN) ===
    {
      icon: 'edit_note',
      label: 'Nhập Điểm',
      route: '/faculty/grading',
      roles: ['Faculty', 'Admin'] // Mục 5: Nhập điểm của sinh viên
    },
    {
      icon: 'assignment',
      label: 'Tra cứu Đăng ký Môn',
      route: '/faculty/enrollments',
      roles: ['Faculty', 'Admin'] // Mục 3: Xem số môn học sinh viên đăng ký
    },

    // === NHÓM BÁO CÁO (CHUNG) ===
    {
      icon: 'assessment',
      label: 'Bảng điểm Tổng hợp',
      route: '/reports/transcripts',
      roles: ['Faculty', 'Admin', 'Student'] // Mục 4: Xem điểm môn học của sinh viên
    },
    {
      icon: 'check_circle',
      label: 'Kết quả Trượt/Đỗ',
      route: '/reports/pass-fail',
      roles: ['Faculty', 'Admin'] // Mục 6: Xem kết quả trượt đỗ
    },
  ];

  // Hàm kiểm tra quyền truy cập (Dùng trong *ngIf)
  isAllowed(roles: string[]): boolean {
    return roles.some(role => this.currentUserRoles.includes(role));
  }
}
