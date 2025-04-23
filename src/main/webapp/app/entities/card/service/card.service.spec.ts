import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICard } from '../card.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../card.test-samples';

import { CardService, RestCard } from './card.service';

const requireRestSample: RestCard = {
  ...sampleWithRequiredData,
  expiryDate: sampleWithRequiredData.expiryDate?.toJSON(),
  blockedAt: sampleWithRequiredData.blockedAt?.toJSON(),
  activatedAt: sampleWithRequiredData.activatedAt?.toJSON(),
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
  closedAt: sampleWithRequiredData.closedAt?.toJSON(),
  dispatchedAt: sampleWithRequiredData.dispatchedAt?.toJSON(),
};

describe('Card Service', () => {
  let service: CardService;
  let httpMock: HttpTestingController;
  let expectedResult: ICard | ICard[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CardService);
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

    it('should create a Card', () => {
      const card = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(card).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Card', () => {
      const card = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(card).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Card', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Card', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Card', () => {
      const expected = true;

      service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCardToCollectionIfMissing', () => {
      it('should add a Card to an empty array', () => {
        const card: ICard = sampleWithRequiredData;
        expectedResult = service.addCardToCollectionIfMissing([], card);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(card);
      });

      it('should not add a Card to an array that contains it', () => {
        const card: ICard = sampleWithRequiredData;
        const cardCollection: ICard[] = [
          {
            ...card,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCardToCollectionIfMissing(cardCollection, card);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Card to an array that doesn't contain it", () => {
        const card: ICard = sampleWithRequiredData;
        const cardCollection: ICard[] = [sampleWithPartialData];
        expectedResult = service.addCardToCollectionIfMissing(cardCollection, card);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(card);
      });

      it('should add only unique Card to an array', () => {
        const cardArray: ICard[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cardCollection: ICard[] = [sampleWithRequiredData];
        expectedResult = service.addCardToCollectionIfMissing(cardCollection, ...cardArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const card: ICard = sampleWithRequiredData;
        const card2: ICard = sampleWithPartialData;
        expectedResult = service.addCardToCollectionIfMissing([], card, card2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(card);
        expect(expectedResult).toContain(card2);
      });

      it('should accept null and undefined values', () => {
        const card: ICard = sampleWithRequiredData;
        expectedResult = service.addCardToCollectionIfMissing([], null, card, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(card);
      });

      it('should return initial array if no Card is added', () => {
        const cardCollection: ICard[] = [sampleWithRequiredData];
        expectedResult = service.addCardToCollectionIfMissing(cardCollection, undefined, null);
        expect(expectedResult).toEqual(cardCollection);
      });
    });

    describe('compareCard', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCard(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' };
        const entity2 = null;

        const compareResult1 = service.compareCard(entity1, entity2);
        const compareResult2 = service.compareCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' };
        const entity2 = { id: '48ab7313-5b00-44ce-be99-b026f6d0ba0c' };

        const compareResult1 = service.compareCard(entity1, entity2);
        const compareResult2 = service.compareCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' };
        const entity2 = { id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' };

        const compareResult1 = service.compareCard(entity1, entity2);
        const compareResult2 = service.compareCard(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
