import dayjs from 'dayjs/esm';
import { ISinhVien } from 'app/entities/sinh-vien/sinh-vien.model';
import { IMonHoc } from 'app/entities/mon-hoc/mon-hoc.model';

export interface IKetQuaHocTap {
  id: number;
  diemQuaTrinh?: number | null;
  diemThanhPhan?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  sinhVien?: ISinhVien | null;
  monHoc?: IMonHoc | null;
}

export type NewKetQuaHocTap = Omit<IKetQuaHocTap, 'id'> & { id: null };
