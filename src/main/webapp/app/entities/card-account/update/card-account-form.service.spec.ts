import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../card-account.test-samples';

import { CardAccountFormService } from './card-account-form.service';

describe('CardAccount Form Service', () => {
  let service: CardAccountFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CardAccountFormService);
  });

  describe('Service methods', () => {
    describe('createCardAccountFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCardAccountFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNumber: expect.any(Object),
            currency: expect.any(Object),
            balance: expect.any(Object),
            reservedAmount: expect.any(Object),
            availableAmount: expect.any(Object),
            blockedAmount: expect.any(Object),
            status: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });

      it('passing ICardAccount should create a new form with FormGroup', () => {
        const formGroup = service.createCardAccountFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            accountNumber: expect.any(Object),
            currency: expect.any(Object),
            balance: expect.any(Object),
            reservedAmount: expect.any(Object),
            availableAmount: expect.any(Object),
            blockedAmount: expect.any(Object),
            status: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
          }),
        );
      });
    });

    describe('getCardAccount', () => {
      it('should return NewCardAccount for default CardAccount initial value', () => {
        const formGroup = service.createCardAccountFormGroup(sampleWithNewData);

        const cardAccount = service.getCardAccount(formGroup) as any;

        expect(cardAccount).toMatchObject(sampleWithNewData);
      });

      it('should return NewCardAccount for empty CardAccount initial value', () => {
        const formGroup = service.createCardAccountFormGroup();

        const cardAccount = service.getCardAccount(formGroup) as any;

        expect(cardAccount).toMatchObject({});
      });

      it('should return ICardAccount', () => {
        const formGroup = service.createCardAccountFormGroup(sampleWithRequiredData);

        const cardAccount = service.getCardAccount(formGroup) as any;

        expect(cardAccount).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICardAccount should not enable id FormControl', () => {
        const formGroup = service.createCardAccountFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCardAccount should disable id FormControl', () => {
        const formGroup = service.createCardAccountFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
