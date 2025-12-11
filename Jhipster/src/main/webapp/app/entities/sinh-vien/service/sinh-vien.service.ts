import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISinhVien, NewSinhVien } from '../sinh-vien.model';
import { KetQuaTruotDo } from '../../../ket-qua-truot-do';

export type PartialUpdateSinhVien = Partial<ISinhVien> & Pick<ISinhVien, 'id'>;

type RestOf<T extends ISinhVien | NewSinhVien> = Omit<T, 'ngaySinh' | 'createdAt' | 'updatedAt'> & {
  ngaySinh?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestSinhVien = RestOf<ISinhVien>;

export type NewRestSinhVien = RestOf<NewSinhVien>;

export type PartialUpdateRestSinhVien = RestOf<PartialUpdateSinhVien>;

export type EntityResponseType = HttpResponse<ISinhVien>;
export type EntityArrayResponseType = HttpResponse<ISinhVien[]>;

@Injectable({ providedIn: 'root' })
export class SinhVienService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sinh-viens');

  create(sinhVien: NewSinhVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sinhVien);
    return this.http
      .post<RestSinhVien>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sinhVien: ISinhVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sinhVien);
    return this.http
      .put<RestSinhVien>(`${this.resourceUrl}/${this.getSinhVienIdentifier(sinhVien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sinhVien: PartialUpdateSinhVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sinhVien);
    return this.http
      .patch<RestSinhVien>(`${this.resourceUrl}/${this.getSinhVienIdentifier(sinhVien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSinhVien>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSinhVien[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSinhVienIdentifier(sinhVien: Pick<ISinhVien, 'id'>): number {
    return sinhVien.id;
  }

  compareSinhVien(o1: Pick<ISinhVien, 'id'> | null, o2: Pick<ISinhVien, 'id'> | null): boolean {
    return o1 && o2 ? this.getSinhVienIdentifier(o1) === this.getSinhVienIdentifier(o2) : o1 === o2;
  }

  addSinhVienToCollectionIfMissing<Type extends Pick<ISinhVien, 'id'>>(
    sinhVienCollection: Type[],
    ...sinhViensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sinhViens: Type[] = sinhViensToCheck.filter(isPresent);
    if (sinhViens.length > 0) {
      const sinhVienCollectionIdentifiers = sinhVienCollection.map(sinhVienItem => this.getSinhVienIdentifier(sinhVienItem));
      const sinhViensToAdd = sinhViens.filter(sinhVienItem => {
        const sinhVienIdentifier = this.getSinhVienIdentifier(sinhVienItem);
        if (sinhVienCollectionIdentifiers.includes(sinhVienIdentifier)) {
          return false;
        }
        sinhVienCollectionIdentifiers.push(sinhVienIdentifier);
        return true;
      });
      return [...sinhViensToAdd, ...sinhVienCollection];
    }
    return sinhVienCollection;
  }

  protected convertDateFromClient<T extends ISinhVien | NewSinhVien | PartialUpdateSinhVien>(sinhVien: T): RestOf<T> {
    return {
      ...sinhVien,
      ngaySinh: sinhVien.ngaySinh?.format(DATE_FORMAT) ?? null,
      createdAt: sinhVien.createdAt?.toJSON() ?? null,
      updatedAt: sinhVien.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restSinhVien: RestSinhVien): ISinhVien {
    return {
      ...restSinhVien,
      ngaySinh: restSinhVien.ngaySinh ? dayjs(restSinhVien.ngaySinh) : undefined,
      createdAt: restSinhVien.createdAt ? dayjs(restSinhVien.createdAt) : undefined,
      updatedAt: restSinhVien.updatedAt ? dayjs(restSinhVien.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSinhVien>): HttpResponse<ISinhVien> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSinhVien[]>): HttpResponse<ISinhVien[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }


  getKetQuaTruotDo(svId: number): Observable<KetQuaTruotDo[]> {
    return this.http.get<KetQuaTruotDo[]>(`http://localhost:8080/api/ket-qua-hoc-taps/ket-qua/${svId}`);
  }

}
