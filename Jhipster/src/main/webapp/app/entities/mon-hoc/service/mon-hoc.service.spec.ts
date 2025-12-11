import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IMonHoc } from '../mon-hoc.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../mon-hoc.test-samples';

import { MonHocService, RestMonHoc } from './mon-hoc.service';

const requireRestSample: RestMonHoc = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('MonHoc Service', () => {
  let service: MonHocService;
  let httpMock: HttpTestingController;
  let expectedResult: IMonHoc | IMonHoc[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(MonHocService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a MonHoc', () => {
      const monHoc = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(monHoc).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MonHoc', () => {
      const monHoc = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(monHoc).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MonHoc', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MonHoc', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MonHoc', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMonHocToCollectionIfMissing', () => {
      it('should add a MonHoc to an empty array', () => {
        const monHoc: IMonHoc = sampleWithRequiredData;
        expectedResult = service.addMonHocToCollectionIfMissing([], monHoc);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(monHoc);
      });

      it('should not add a MonHoc to an array that contains it', () => {
        const monHoc: IMonHoc = sampleWithRequiredData;
        const monHocCollection: IMonHoc[] = [
          {
            ...monHoc,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMonHocToCollectionIfMissing(monHocCollection, monHoc);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MonHoc to an array that doesn't contain it", () => {
        const monHoc: IMonHoc = sampleWithRequiredData;
        const monHocCollection: IMonHoc[] = [sampleWithPartialData];
        expectedResult = service.addMonHocToCollectionIfMissing(monHocCollection, monHoc);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(monHoc);
      });

      it('should add only unique MonHoc to an array', () => {
        const monHocArray: IMonHoc[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const monHocCollection: IMonHoc[] = [sampleWithRequiredData];
        expectedResult = service.addMonHocToCollectionIfMissing(monHocCollection, ...monHocArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const monHoc: IMonHoc = sampleWithRequiredData;
        const monHoc2: IMonHoc = sampleWithPartialData;
        expectedResult = service.addMonHocToCollectionIfMissing([], monHoc, monHoc2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(monHoc);
        expect(expectedResult).toContain(monHoc2);
      });

      it('should accept null and undefined values', () => {
        const monHoc: IMonHoc = sampleWithRequiredData;
        expectedResult = service.addMonHocToCollectionIfMissing([], null, monHoc, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(monHoc);
      });

      it('should return initial array if no MonHoc is added', () => {
        const monHocCollection: IMonHoc[] = [sampleWithRequiredData];
        expectedResult = service.addMonHocToCollectionIfMissing(monHocCollection, undefined, null);
        expect(expectedResult).toEqual(monHocCollection);
      });
    });

    describe('compareMonHoc', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMonHoc(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 24990 };
        const entity2 = null;

        const compareResult1 = service.compareMonHoc(entity1, entity2);
        const compareResult2 = service.compareMonHoc(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 24990 };
        const entity2 = { id: 8630 };

        const compareResult1 = service.compareMonHoc(entity1, entity2);
        const compareResult2 = service.compareMonHoc(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 24990 };
        const entity2 = { id: 24990 };

        const compareResult1 = service.compareMonHoc(entity1, entity2);
        const compareResult2 = service.compareMonHoc(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
