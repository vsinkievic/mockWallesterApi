import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../account-statement-record.test-samples';

import { AccountStatementRecordFormService } from './account-statement-record-form.service';

describe('AccountStatementRecord Form Service', () => {
  let service: AccountStatementRecordFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountStatementRecordFormService);
  });

  describe('Service methods', () => {
    describe('createAccountStatementRecordFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccountStatementRecordFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cardId: expect.any(Object),
            type: expect.any(Object),
            group: expect.any(Object),
            date: expect.any(Object),
            transactionAmount: expect.any(Object),
            transactionCurrencyCode: expect.any(Object),
            accountAmount: expect.any(Object),
            accountCurrencyCode: expect.any(Object),
            merchantCategoryCode: expect.any(Object),
            merchantId: expect.any(Object),
            terminalId: expect.any(Object),
            merchantName: expect.any(Object),
            merchantCity: expect.any(Object),
            merchantCountryCode: expect.any(Object),
            description: expect.any(Object),
            originalAuthorizationId: expect.any(Object),
            isReversal: expect.any(Object),
            isDeclined: expect.any(Object),
            isCleared: expect.any(Object),
            markedForDisputeAt: expect.any(Object),
            markedForDisputeBy: expect.any(Object),
            status: expect.any(Object),
            response: expect.any(Object),
            responseCode: expect.any(Object),
            accountExternalId: expect.any(Object),
            maskedCardNumber: expect.any(Object),
            hasPaymentDocumentFiles: expect.any(Object),
            hasPaymentNotes: expect.any(Object),
            cardName: expect.any(Object),
            embossingName: expect.any(Object),
            embossingFirstName: expect.any(Object),
            embossingLastName: expect.any(Object),
            embossingCompanyName: expect.any(Object),
            subType: expect.any(Object),
            purchaseDate: expect.any(Object),
            exchangeRate: expect.any(Object),
            enrichedMerchantName: expect.any(Object),
            enrichedMerchantUrl: expect.any(Object),
            enrichedMerchantDomain: expect.any(Object),
            enrichedMerchantTelephone: expect.any(Object),
            enrichedMerchantIconUrl: expect.any(Object),
            totalAmount: expect.any(Object),
          }),
        );
      });

      it('passing IAccountStatementRecord should create a new form with FormGroup', () => {
        const formGroup = service.createAccountStatementRecordFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cardId: expect.any(Object),
            type: expect.any(Object),
            group: expect.any(Object),
            date: expect.any(Object),
            transactionAmount: expect.any(Object),
            transactionCurrencyCode: expect.any(Object),
            accountAmount: expect.any(Object),
            accountCurrencyCode: expect.any(Object),
            merchantCategoryCode: expect.any(Object),
            merchantId: expect.any(Object),
            terminalId: expect.any(Object),
            merchantName: expect.any(Object),
            merchantCity: expect.any(Object),
            merchantCountryCode: expect.any(Object),
            description: expect.any(Object),
            originalAuthorizationId: expect.any(Object),
            isReversal: expect.any(Object),
            isDeclined: expect.any(Object),
            isCleared: expect.any(Object),
            markedForDisputeAt: expect.any(Object),
            markedForDisputeBy: expect.any(Object),
            status: expect.any(Object),
            response: expect.any(Object),
            responseCode: expect.any(Object),
            accountExternalId: expect.any(Object),
            maskedCardNumber: expect.any(Object),
            hasPaymentDocumentFiles: expect.any(Object),
            hasPaymentNotes: expect.any(Object),
            cardName: expect.any(Object),
            embossingName: expect.any(Object),
            embossingFirstName: expect.any(Object),
            embossingLastName: expect.any(Object),
            embossingCompanyName: expect.any(Object),
            subType: expect.any(Object),
            purchaseDate: expect.any(Object),
            exchangeRate: expect.any(Object),
            enrichedMerchantName: expect.any(Object),
            enrichedMerchantUrl: expect.any(Object),
            enrichedMerchantDomain: expect.any(Object),
            enrichedMerchantTelephone: expect.any(Object),
            enrichedMerchantIconUrl: expect.any(Object),
            totalAmount: expect.any(Object),
          }),
        );
      });
    });

    describe('getAccountStatementRecord', () => {
      it('should return NewAccountStatementRecord for default AccountStatementRecord initial value', () => {
        const formGroup = service.createAccountStatementRecordFormGroup(sampleWithNewData);

        const accountStatementRecord = service.getAccountStatementRecord(formGroup) as any;

        expect(accountStatementRecord).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccountStatementRecord for empty AccountStatementRecord initial value', () => {
        const formGroup = service.createAccountStatementRecordFormGroup();

        const accountStatementRecord = service.getAccountStatementRecord(formGroup) as any;

        expect(accountStatementRecord).toMatchObject({});
      });

      it('should return IAccountStatementRecord', () => {
        const formGroup = service.createAccountStatementRecordFormGroup(sampleWithRequiredData);

        const accountStatementRecord = service.getAccountStatementRecord(formGroup) as any;

        expect(accountStatementRecord).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccountStatementRecord should not enable id FormControl', () => {
        const formGroup = service.createAccountStatementRecordFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAccountStatementRecord should disable id FormControl', () => {
        const formGroup = service.createAccountStatementRecordFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
