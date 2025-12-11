import dayjs from 'dayjs/esm';

import { IMonHoc, NewMonHoc } from './mon-hoc.model';

export const sampleWithRequiredData: IMonHoc = {
  id: 10618,
  tenMon: 'unless',
  tyLeDiemQuaTrinh: 10059.18,
};

export const sampleWithPartialData: IMonHoc = {
  id: 10784,
  tenMon: 'shampoo lest',
  tyLeDiemQuaTrinh: 9700.35,
  createdAt: dayjs('2025-12-09T18:42'),
  updatedAt: dayjs('2025-12-09T11:23'),
};

export const sampleWithFullData: IMonHoc = {
  id: 17088,
  tenMon: 'whereas naturally from',
  tyLeDiemQuaTrinh: 1351.53,
  createdAt: dayjs('2025-12-09T18:02'),
  updatedAt: dayjs('2025-12-09T16:34'),
};

export const sampleWithNewData: NewMonHoc = {
  tenMon: 'commonly ambitious',
  tyLeDiemQuaTrinh: 4357.39,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
