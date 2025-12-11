import dayjs from 'dayjs/esm';

export interface IMonHoc {
  id: number;
  tenMon?: string | null;
  tyLeDiemQuaTrinh?: number | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewMonHoc = Omit<IMonHoc, 'id'> & { id: null };
