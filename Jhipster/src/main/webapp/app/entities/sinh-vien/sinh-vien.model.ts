import dayjs from 'dayjs/esm';

export interface ISinhVien {
  id: number;
  tenSv?: string | null;
  gioiTinh?: string | null;
  ngaySinh?: dayjs.Dayjs | null;
  lop?: string | null;
  khoa?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewSinhVien = Omit<ISinhVien, 'id'> & { id: null };
