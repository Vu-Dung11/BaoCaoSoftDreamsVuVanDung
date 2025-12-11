import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMonHoc, NewMonHoc } from '../mon-hoc.model';

export type PartialUpdateMonHoc = Partial<IMonHoc> & Pick<IMonHoc, 'id'>;

type RestOf<T extends IMonHoc | NewMonHoc> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestMonHoc = RestOf<IMonHoc>;

export type NewRestMonHoc = RestOf<NewMonHoc>;

export type PartialUpdateRestMonHoc = RestOf<PartialUpdateMonHoc>;

export type EntityResponseType = HttpResponse<IMonHoc>;
export type EntityArrayResponseType = HttpResponse<IMonHoc[]>;

@Injectable({ providedIn: 'root' })
export class MonHocService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mon-hocs');

  create(monHoc: NewMonHoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monHoc);
    return this.http
      .post<RestMonHoc>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(monHoc: IMonHoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monHoc);
    return this.http
      .put<RestMonHoc>(`${this.resourceUrl}/${this.getMonHocIdentifier(monHoc)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(monHoc: PartialUpdateMonHoc): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(monHoc);
    return this.http
      .patch<RestMonHoc>(`${this.resourceUrl}/${this.getMonHocIdentifier(monHoc)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestMonHoc>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestMonHoc[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMonHocIdentifier(monHoc: Pick<IMonHoc, 'id'>): number {
    return monHoc.id;
  }

  compareMonHoc(o1: Pick<IMonHoc, 'id'> | null, o2: Pick<IMonHoc, 'id'> | null): boolean {
    return o1 && o2 ? this.getMonHocIdentifier(o1) === this.getMonHocIdentifier(o2) : o1 === o2;
  }

  addMonHocToCollectionIfMissing<Type extends Pick<IMonHoc, 'id'>>(
    monHocCollection: Type[],
    ...monHocsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const monHocs: Type[] = monHocsToCheck.filter(isPresent);
    if (monHocs.length > 0) {
      const monHocCollectionIdentifiers = monHocCollection.map(monHocItem => this.getMonHocIdentifier(monHocItem));
      const monHocsToAdd = monHocs.filter(monHocItem => {
        const monHocIdentifier = this.getMonHocIdentifier(monHocItem);
        if (monHocCollectionIdentifiers.includes(monHocIdentifier)) {
          return false;
        }
        monHocCollectionIdentifiers.push(monHocIdentifier);
        return true;
      });
      return [...monHocsToAdd, ...monHocCollection];
    }
    return monHocCollection;
  }

  protected convertDateFromClient<T extends IMonHoc | NewMonHoc | PartialUpdateMonHoc>(monHoc: T): RestOf<T> {
    return {
      ...monHoc,
      createdAt: monHoc.createdAt?.toJSON() ?? null,
      updatedAt: monHoc.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restMonHoc: RestMonHoc): IMonHoc {
    return {
      ...restMonHoc,
      createdAt: restMonHoc.createdAt ? dayjs(restMonHoc.createdAt) : undefined,
      updatedAt: restMonHoc.updatedAt ? dayjs(restMonHoc.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestMonHoc>): HttpResponse<IMonHoc> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestMonHoc[]>): HttpResponse<IMonHoc[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
