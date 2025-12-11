import dayjs from 'dayjs/esm';

import { IKetQuaHocTap, NewKetQuaHocTap } from './ket-qua-hoc-tap.model';

export const sampleWithRequiredData: IKetQuaHocTap = {
  id: 8147,
};

export const sampleWithPartialData: IKetQuaHocTap = {
  id: 15066,
  diemQuaTrinh: 3391.53,
  updatedAt: dayjs('2025-12-09T20:51'),
};

export const sampleWithFullData: IKetQuaHocTap = {
  id: 3082,
  diemQuaTrinh: 19666.67,
  diemThanhPhan: 26434.92,
  createdAt: dayjs('2025-12-10T06:11'),
  updatedAt: dayjs('2025-12-10T02:22'),
};

export const sampleWithNewData: NewKetQuaHocTap = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
