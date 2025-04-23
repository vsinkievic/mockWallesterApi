import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../card.test-samples';

import { CardFormService } from './card-form.service';

describe('Card Form Service', () => {
  let service: CardFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CardFormService);
  });

  describe('Service methods', () => {
    describe('createCardFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCardFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            predecessorCardId: expect.any(Object),
            accountId: expect.any(Object),
            personId: expect.any(Object),
            externalId: expect.any(Object),
            type: expect.any(Object),
            name: expect.any(Object),
            maskedCardNumber: expect.any(Object),
            referenceNumber: expect.any(Object),
            expiryDate: expect.any(Object),
            blockType: expect.any(Object),
            blockedAt: expect.any(Object),
            blockedBy: expect.any(Object),
            status: expect.any(Object),
            embossingName: expect.any(Object),
            embossingFirstName: expect.any(Object),
            embossingLastName: expect.any(Object),
            embossingCompanyName: expect.any(Object),
            limitDailyPurchase: expect.any(Object),
            limitDailyWithdrawal: expect.any(Object),
            limitMonthlyPurchase: expect.any(Object),
            limitMonthlyWithdrawal: expect.any(Object),
            limitTransactionPurchase: expect.any(Object),
            secure3DType: expect.any(Object),
            secure3DMobile: expect.any(Object),
            secure3DEmail: expect.any(Object),
            secure3DLanguageCode: expect.any(Object),
            secure3DOutOfBandEnabled: expect.any(Object),
            secure3DOutOfBandId: expect.any(Object),
            deliveryFirstName: expect.any(Object),
            deliveryLastName: expect.any(Object),
            deliveryCompanyName: expect.any(Object),
            deliveryAddress1: expect.any(Object),
            deliveryAddress2: expect.any(Object),
            deliveryPostalCode: expect.any(Object),
            deliveryCity: expect.any(Object),
            deliveryCountryCode: expect.any(Object),
            deliveryDispatchMethod: expect.any(Object),
            deliveryPhone: expect.any(Object),
            deliveryTrackingNumber: expect.any(Object),
            isEnrolledFor3DSecure: expect.any(Object),
            isCard3DSecureActivated: expect.any(Object),
            renewAutomatically: expect.any(Object),
            isDisposable: expect.any(Object),
            securityContactlessEnabled: expect.any(Object),
            securityWithdrawalEnabled: expect.any(Object),
            securityInternetPurchaseEnabled: expect.any(Object),
            securityOverallLimitsEnabled: expect.any(Object),
            securityAllTimeLimitsEnabled: expect.any(Object),
            personalizationProductCode: expect.any(Object),
            carrierType: expect.any(Object),
            cardMetadataProfileId: expect.any(Object),
            activatedAt: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            closedAt: expect.any(Object),
            closedBy: expect.any(Object),
            closeReason: expect.any(Object),
            companyId: expect.any(Object),
            dispatchedAt: expect.any(Object),
            notificationReceiptsReminderEnabled: expect.any(Object),
            notificationInstantSpendUpdateEnabled: expect.any(Object),
            disposableType: expect.any(Object),
          }),
        );
      });

      it('passing ICard should create a new form with FormGroup', () => {
        const formGroup = service.createCardFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            predecessorCardId: expect.any(Object),
            accountId: expect.any(Object),
            personId: expect.any(Object),
            externalId: expect.any(Object),
            type: expect.any(Object),
            name: expect.any(Object),
            maskedCardNumber: expect.any(Object),
            referenceNumber: expect.any(Object),
            expiryDate: expect.any(Object),
            blockType: expect.any(Object),
            blockedAt: expect.any(Object),
            blockedBy: expect.any(Object),
            status: expect.any(Object),
            embossingName: expect.any(Object),
            embossingFirstName: expect.any(Object),
            embossingLastName: expect.any(Object),
            embossingCompanyName: expect.any(Object),
            limitDailyPurchase: expect.any(Object),
            limitDailyWithdrawal: expect.any(Object),
            limitMonthlyPurchase: expect.any(Object),
            limitMonthlyWithdrawal: expect.any(Object),
            limitTransactionPurchase: expect.any(Object),
            secure3DType: expect.any(Object),
            secure3DMobile: expect.any(Object),
            secure3DEmail: expect.any(Object),
            secure3DLanguageCode: expect.any(Object),
            secure3DOutOfBandEnabled: expect.any(Object),
            secure3DOutOfBandId: expect.any(Object),
            deliveryFirstName: expect.any(Object),
            deliveryLastName: expect.any(Object),
            deliveryCompanyName: expect.any(Object),
            deliveryAddress1: expect.any(Object),
            deliveryAddress2: expect.any(Object),
            deliveryPostalCode: expect.any(Object),
            deliveryCity: expect.any(Object),
            deliveryCountryCode: expect.any(Object),
            deliveryDispatchMethod: expect.any(Object),
            deliveryPhone: expect.any(Object),
            deliveryTrackingNumber: expect.any(Object),
            isEnrolledFor3DSecure: expect.any(Object),
            isCard3DSecureActivated: expect.any(Object),
            renewAutomatically: expect.any(Object),
            isDisposable: expect.any(Object),
            securityContactlessEnabled: expect.any(Object),
            securityWithdrawalEnabled: expect.any(Object),
            securityInternetPurchaseEnabled: expect.any(Object),
            securityOverallLimitsEnabled: expect.any(Object),
            securityAllTimeLimitsEnabled: expect.any(Object),
            personalizationProductCode: expect.any(Object),
            carrierType: expect.any(Object),
            cardMetadataProfileId: expect.any(Object),
            activatedAt: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            closedAt: expect.any(Object),
            closedBy: expect.any(Object),
            closeReason: expect.any(Object),
            companyId: expect.any(Object),
            dispatchedAt: expect.any(Object),
            notificationReceiptsReminderEnabled: expect.any(Object),
            notificationInstantSpendUpdateEnabled: expect.any(Object),
            disposableType: expect.any(Object),
          }),
        );
      });
    });

    describe('getCard', () => {
      it('should return NewCard for default Card initial value', () => {
        const formGroup = service.createCardFormGroup(sampleWithNewData);

        const card = service.getCard(formGroup) as any;

        expect(card).toMatchObject(sampleWithNewData);
      });

      it('should return NewCard for empty Card initial value', () => {
        const formGroup = service.createCardFormGroup();

        const card = service.getCard(formGroup) as any;

        expect(card).toMatchObject({});
      });

      it('should return ICard', () => {
        const formGroup = service.createCardFormGroup(sampleWithRequiredData);

        const card = service.getCard(formGroup) as any;

        expect(card).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICard should not enable id FormControl', () => {
        const formGroup = service.createCardFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCard should disable id FormControl', () => {
        const formGroup = service.createCardFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
