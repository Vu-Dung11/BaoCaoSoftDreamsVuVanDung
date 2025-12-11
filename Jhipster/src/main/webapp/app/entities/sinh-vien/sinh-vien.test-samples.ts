import dayjs from 'dayjs/esm';

import { ISinhVien, NewSinhVien } from './sinh-vien.model';

export const sampleWithRequiredData: ISinhVien = {
  id: 15039,
  tenSv: 'hastily indeed',
  gioiTinh: 'though del',
};

export const sampleWithPartialData: ISinhVien = {
  id: 9321,
  tenSv: 'supposing thump',
  gioiTinh: 'prance nea',
  ngaySinh: dayjs('2025-12-09'),
};

export const sampleWithFullData: ISinhVien = {
  id: 32580,
  tenSv: 'celsius merrily',
  gioiTinh: 'which swan',
  ngaySinh: dayjs('2025-12-10'),
  lop: 'simplistic duh hundred',
  khoa: 'institutionalize trash',
  createdAt: dayjs('2025-12-09T17:32'),
  updatedAt: dayjs('2025-12-10T00:42'),
};

export const sampleWithNewData: NewSinhVien = {
  tenSv: 'down yippee yowza',
  gioiTinh: 'charming a',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
