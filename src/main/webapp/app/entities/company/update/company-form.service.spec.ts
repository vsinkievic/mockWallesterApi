import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../company.test-samples';

import { CompanyFormService } from './company-form.service';

describe('Company Form Service', () => {
  let service: CompanyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            registrationNumber: expect.any(Object),
            regAddressCountryCode: expect.any(Object),
            regAddress1: expect.any(Object),
            regAddress2: expect.any(Object),
            regAddressCity: expect.any(Object),
            regAddressPostalCode: expect.any(Object),
            hqAddressCountryCode: expect.any(Object),
            hqAddress1: expect.any(Object),
            hqAddress2: expect.any(Object),
            hqAddressCity: expect.any(Object),
            hqAddressPostalCode: expect.any(Object),
            riskProfile: expect.any(Object),
            mobile: expect.any(Object),
            email: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
            deletedAt: expect.any(Object),
            deletedBy: expect.any(Object),
            industryType: expect.any(Object),
            dateOfIncorporation: expect.any(Object),
            businessRelationshipPurpose: expect.any(Object),
            isSanctionsRelated: expect.any(Object),
            isAdverseMediaInvolved: expect.any(Object),
            employeesQuantity: expect.any(Object),
            cardSpendingAmount: expect.any(Object),
            limitDailyPurchase: expect.any(Object),
            limitDailyWithdrawal: expect.any(Object),
            limitMonthlyPurchase: expect.any(Object),
            limitMonthlyWithdrawal: expect.any(Object),
            kybStatus: expect.any(Object),
            status: expect.any(Object),
            pushNotificationsEnabled: expect.any(Object),
            preferredLanguageCode: expect.any(Object),
            vatNumber: expect.any(Object),
          }),
        );
      });

      it('passing ICompany should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            registrationNumber: expect.any(Object),
            regAddressCountryCode: expect.any(Object),
            regAddress1: expect.any(Object),
            regAddress2: expect.any(Object),
            regAddressCity: expect.any(Object),
            regAddressPostalCode: expect.any(Object),
            hqAddressCountryCode: expect.any(Object),
            hqAddress1: expect.any(Object),
            hqAddress2: expect.any(Object),
            hqAddressCity: expect.any(Object),
            hqAddressPostalCode: expect.any(Object),
            riskProfile: expect.any(Object),
            mobile: expect.any(Object),
            email: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
            deletedAt: expect.any(Object),
            deletedBy: expect.any(Object),
            industryType: expect.any(Object),
            dateOfIncorporation: expect.any(Object),
            businessRelationshipPurpose: expect.any(Object),
            isSanctionsRelated: expect.any(Object),
            isAdverseMediaInvolved: expect.any(Object),
            employeesQuantity: expect.any(Object),
            cardSpendingAmount: expect.any(Object),
            limitDailyPurchase: expect.any(Object),
            limitDailyWithdrawal: expect.any(Object),
            limitMonthlyPurchase: expect.any(Object),
            limitMonthlyWithdrawal: expect.any(Object),
            kybStatus: expect.any(Object),
            status: expect.any(Object),
            pushNotificationsEnabled: expect.any(Object),
            preferredLanguageCode: expect.any(Object),
            vatNumber: expect.any(Object),
          }),
        );
      });
    });

    describe('getCompany', () => {
      it('should return NewCompany for default Company initial value', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithNewData);

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompany for empty Company initial value', () => {
        const formGroup = service.createCompanyFormGroup();

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject({});
      });

      it('should return ICompany', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);

        const company = service.getCompany(formGroup) as any;

        expect(company).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompany should not enable id FormControl', () => {
        const formGroup = service.createCompanyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompany should disable id FormControl', () => {
        const formGroup = service.createCompanyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
