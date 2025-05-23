import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICompany } from '../company.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../company.test-samples';

import { CompanyService, RestCompany } from './company.service';

const requireRestSample: RestCompany = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
  deletedAt: sampleWithRequiredData.deletedAt?.toJSON(),
  dateOfIncorporation: sampleWithRequiredData.dateOfIncorporation?.toJSON(),
};

describe('Company Service', () => {
  let service: CompanyService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompany | ICompany[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CompanyService);
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

    it('should create a Company', () => {
      const company = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(company).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Company', () => {
      const company = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(company).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Company', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Company', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Company', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompanyToCollectionIfMissing', () => {
      it('should add a Company to an empty array', () => {
        const company: ICompany = sampleWithRequiredData;
        expectedResult = service.addCompanyToCollectionIfMissing([], company);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(company);
      });

      it('should not add a Company to an array that contains it', () => {
        const company: ICompany = sampleWithRequiredData;
        const companyCollection: ICompany[] = [
          {
            ...company,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompanyToCollectionIfMissing(companyCollection, company);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Company to an array that doesn't contain it", () => {
        const company: ICompany = sampleWithRequiredData;
        const companyCollection: ICompany[] = [sampleWithPartialData];
        expectedResult = service.addCompanyToCollectionIfMissing(companyCollection, company);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(company);
      });

      it('should add only unique Company to an array', () => {
        const companyArray: ICompany[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const companyCollection: ICompany[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyToCollectionIfMissing(companyCollection, ...companyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const company: ICompany = sampleWithRequiredData;
        const company2: ICompany = sampleWithPartialData;
        expectedResult = service.addCompanyToCollectionIfMissing([], company, company2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(company);
        expect(expectedResult).toContain(company2);
      });

      it('should accept null and undefined values', () => {
        const company: ICompany = sampleWithRequiredData;
        expectedResult = service.addCompanyToCollectionIfMissing([], null, company, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(company);
      });

      it('should return initial array if no Company is added', () => {
        const companyCollection: ICompany[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyToCollectionIfMissing(companyCollection, undefined, null);
        expect(expectedResult).toEqual(companyCollection);
      });
    });

    describe('compareCompany', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompany(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 'e3c7007e-9185-4b2d-90f0-9e5ec8b5921f' };
        const entity2 = null;

        const compareResult1 = service.compareCompany(entity1, entity2);
        const compareResult2 = service.compareCompany(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 'e3c7007e-9185-4b2d-90f0-9e5ec8b5921f' };
        const entity2 = { id: '923bfdd4-0968-49ec-af05-6987429bf810' };

        const compareResult1 = service.compareCompany(entity1, entity2);
        const compareResult2 = service.compareCompany(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 'e3c7007e-9185-4b2d-90f0-9e5ec8b5921f' };
        const entity2 = { id: 'e3c7007e-9185-4b2d-90f0-9e5ec8b5921f' };

        const compareResult1 = service.compareCompany(entity1, entity2);
        const compareResult2 = service.compareCompany(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
