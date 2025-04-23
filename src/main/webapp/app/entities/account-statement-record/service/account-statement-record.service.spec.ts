import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAccountStatementRecord } from '../account-statement-record.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../account-statement-record.test-samples';

import { AccountStatementRecordService, RestAccountStatementRecord } from './account-statement-record.service';

const requireRestSample: RestAccountStatementRecord = {
  ...sampleWithRequiredData,
  date: sampleWithRequiredData.date?.toJSON(),
  markedForDisputeAt: sampleWithRequiredData.markedForDisputeAt?.toJSON(),
  purchaseDate: sampleWithRequiredData.purchaseDate?.toJSON(),
};

describe('AccountStatementRecord Service', () => {
  let service: AccountStatementRecordService;
  let httpMock: HttpTestingController;
  let expectedResult: IAccountStatementRecord | IAccountStatementRecord[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AccountStatementRecordService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AccountStatementRecord', () => {
      const accountStatementRecord = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(accountStatementRecord).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AccountStatementRecord', () => {
      const accountStatementRecord = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(accountStatementRecord).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AccountStatementRecord', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AccountStatementRecord', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AccountStatementRecord', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAccountStatementRecordToCollectionIfMissing', () => {
      it('should add a AccountStatementRecord to an empty array', () => {
        const accountStatementRecord: IAccountStatementRecord = sampleWithRequiredData;
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing([], accountStatementRecord);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accountStatementRecord);
      });

      it('should not add a AccountStatementRecord to an array that contains it', () => {
        const accountStatementRecord: IAccountStatementRecord = sampleWithRequiredData;
        const accountStatementRecordCollection: IAccountStatementRecord[] = [
          {
            ...accountStatementRecord,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing(accountStatementRecordCollection, accountStatementRecord);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AccountStatementRecord to an array that doesn't contain it", () => {
        const accountStatementRecord: IAccountStatementRecord = sampleWithRequiredData;
        const accountStatementRecordCollection: IAccountStatementRecord[] = [sampleWithPartialData];
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing(accountStatementRecordCollection, accountStatementRecord);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accountStatementRecord);
      });

      it('should add only unique AccountStatementRecord to an array', () => {
        const accountStatementRecordArray: IAccountStatementRecord[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const accountStatementRecordCollection: IAccountStatementRecord[] = [sampleWithRequiredData];
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing(
          accountStatementRecordCollection,
          ...accountStatementRecordArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const accountStatementRecord: IAccountStatementRecord = sampleWithRequiredData;
        const accountStatementRecord2: IAccountStatementRecord = sampleWithPartialData;
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing([], accountStatementRecord, accountStatementRecord2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(accountStatementRecord);
        expect(expectedResult).toContain(accountStatementRecord2);
      });

      it('should accept null and undefined values', () => {
        const accountStatementRecord: IAccountStatementRecord = sampleWithRequiredData;
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing([], null, accountStatementRecord, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(accountStatementRecord);
      });

      it('should return initial array if no AccountStatementRecord is added', () => {
        const accountStatementRecordCollection: IAccountStatementRecord[] = [sampleWithRequiredData];
        expectedResult = service.addAccountStatementRecordToCollectionIfMissing(accountStatementRecordCollection, undefined, null);
        expect(expectedResult).toEqual(accountStatementRecordCollection);
      });
    });

    describe('compareAccountStatementRecord', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAccountStatementRecord(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
        const entity2 = null;

        const compareResult1 = service.compareAccountStatementRecord(entity1, entity2);
        const compareResult2 = service.compareAccountStatementRecord(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
        const entity2 = { id: '1c489a8c-dd9b-4903-a4eb-3fa38058e239' };

        const compareResult1 = service.compareAccountStatementRecord(entity1, entity2);
        const compareResult2 = service.compareAccountStatementRecord(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
        const entity2 = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };

        const compareResult1 = service.compareAccountStatementRecord(entity1, entity2);
        const compareResult2 = service.compareAccountStatementRecord(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
