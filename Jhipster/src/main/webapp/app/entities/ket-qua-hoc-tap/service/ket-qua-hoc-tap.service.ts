import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKetQuaHocTap, NewKetQuaHocTap } from '../ket-qua-hoc-tap.model';

export type PartialUpdateKetQuaHocTap = Partial<IKetQuaHocTap> & Pick<IKetQuaHocTap, 'id'>;

type RestOf<T extends IKetQuaHocTap | NewKetQuaHocTap> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestKetQuaHocTap = RestOf<IKetQuaHocTap>;

export type NewRestKetQuaHocTap = RestOf<NewKetQuaHocTap>;

export type PartialUpdateRestKetQuaHocTap = RestOf<PartialUpdateKetQuaHocTap>;

export type EntityResponseType = HttpResponse<IKetQuaHocTap>;
export type EntityArrayResponseType = HttpResponse<IKetQuaHocTap[]>;

@Injectable({ providedIn: 'root' })
export class KetQuaHocTapService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ket-qua-hoc-taps');

  create(ketQuaHocTap: NewKetQuaHocTap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ketQuaHocTap);
    return this.http
      .post<RestKetQuaHocTap>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ketQuaHocTap: IKetQuaHocTap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ketQuaHocTap);
    return this.http
      .put<RestKetQuaHocTap>(`${this.resourceUrl}/${this.getKetQuaHocTapIdentifier(ketQuaHocTap)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ketQuaHocTap: PartialUpdateKetQuaHocTap): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ketQuaHocTap);
    return this.http
      .patch<RestKetQuaHocTap>(`${this.resourceUrl}/${this.getKetQuaHocTapIdentifier(ketQuaHocTap)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKetQuaHocTap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKetQuaHocTap[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKetQuaHocTapIdentifier(ketQuaHocTap: Pick<IKetQuaHocTap, 'id'>): number {
    return ketQuaHocTap.id;
  }

  compareKetQuaHocTap(o1: Pick<IKetQuaHocTap, 'id'> | null, o2: Pick<IKetQuaHocTap, 'id'> | null): boolean {
    return o1 && o2 ? this.getKetQuaHocTapIdentifier(o1) === this.getKetQuaHocTapIdentifier(o2) : o1 === o2;
  }

  addKetQuaHocTapToCollectionIfMissing<Type extends Pick<IKetQuaHocTap, 'id'>>(
    ketQuaHocTapCollection: Type[],
    ...ketQuaHocTapsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ketQuaHocTaps: Type[] = ketQuaHocTapsToCheck.filter(isPresent);
    if (ketQuaHocTaps.length > 0) {
      const ketQuaHocTapCollectionIdentifiers = ketQuaHocTapCollection.map(ketQuaHocTapItem =>
        this.getKetQuaHocTapIdentifier(ketQuaHocTapItem),
      );
      const ketQuaHocTapsToAdd = ketQuaHocTaps.filter(ketQuaHocTapItem => {
        const ketQuaHocTapIdentifier = this.getKetQuaHocTapIdentifier(ketQuaHocTapItem);
        if (ketQuaHocTapCollectionIdentifiers.includes(ketQuaHocTapIdentifier)) {
          return false;
        }
        ketQuaHocTapCollectionIdentifiers.push(ketQuaHocTapIdentifier);
        return true;
      });
      return [...ketQuaHocTapsToAdd, ...ketQuaHocTapCollection];
    }
    return ketQuaHocTapCollection;
  }

  protected convertDateFromClient<T extends IKetQuaHocTap | NewKetQuaHocTap | PartialUpdateKetQuaHocTap>(ketQuaHocTap: T): RestOf<T> {
    return {
      ...ketQuaHocTap,
      createdAt: ketQuaHocTap.createdAt?.toJSON() ?? null,
      updatedAt: ketQuaHocTap.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKetQuaHocTap: RestKetQuaHocTap): IKetQuaHocTap {
    return {
      ...restKetQuaHocTap,
      createdAt: restKetQuaHocTap.createdAt ? dayjs(restKetQuaHocTap.createdAt) : undefined,
      updatedAt: restKetQuaHocTap.updatedAt ? dayjs(restKetQuaHocTap.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKetQuaHocTap>): HttpResponse<IKetQuaHocTap> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKetQuaHocTap[]>): HttpResponse<IKetQuaHocTap[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
