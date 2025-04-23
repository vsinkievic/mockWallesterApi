import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICompany, NewCompany } from '../company.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompany for edit and NewCompanyFormGroupInput for create.
 */
type CompanyFormGroupInput = ICompany | PartialWithRequiredKeyOf<NewCompany>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICompany | NewCompany> = Omit<T, 'createdAt' | 'updatedAt' | 'deletedAt' | 'dateOfIncorporation'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
  deletedAt?: string | null;
  dateOfIncorporation?: string | null;
};

type CompanyFormRawValue = FormValueOf<ICompany>;

type NewCompanyFormRawValue = FormValueOf<NewCompany>;

type CompanyFormDefaults = Pick<
  NewCompany,
  | 'id'
  | 'createdAt'
  | 'updatedAt'
  | 'deletedAt'
  | 'dateOfIncorporation'
  | 'isSanctionsRelated'
  | 'isAdverseMediaInvolved'
  | 'pushNotificationsEnabled'
>;

type CompanyFormGroupContent = {
  id: FormControl<CompanyFormRawValue['id'] | NewCompany['id']>;
  name: FormControl<CompanyFormRawValue['name']>;
  registrationNumber: FormControl<CompanyFormRawValue['registrationNumber']>;
  regAddressCountryCode: FormControl<CompanyFormRawValue['regAddressCountryCode']>;
  regAddress1: FormControl<CompanyFormRawValue['regAddress1']>;
  regAddress2: FormControl<CompanyFormRawValue['regAddress2']>;
  regAddressCity: FormControl<CompanyFormRawValue['regAddressCity']>;
  regAddressPostalCode: FormControl<CompanyFormRawValue['regAddressPostalCode']>;
  hqAddressCountryCode: FormControl<CompanyFormRawValue['hqAddressCountryCode']>;
  hqAddress1: FormControl<CompanyFormRawValue['hqAddress1']>;
  hqAddress2: FormControl<CompanyFormRawValue['hqAddress2']>;
  hqAddressCity: FormControl<CompanyFormRawValue['hqAddressCity']>;
  hqAddressPostalCode: FormControl<CompanyFormRawValue['hqAddressPostalCode']>;
  riskProfile: FormControl<CompanyFormRawValue['riskProfile']>;
  mobile: FormControl<CompanyFormRawValue['mobile']>;
  email: FormControl<CompanyFormRawValue['email']>;
  createdAt: FormControl<CompanyFormRawValue['createdAt']>;
  createdBy: FormControl<CompanyFormRawValue['createdBy']>;
  updatedAt: FormControl<CompanyFormRawValue['updatedAt']>;
  updatedBy: FormControl<CompanyFormRawValue['updatedBy']>;
  deletedAt: FormControl<CompanyFormRawValue['deletedAt']>;
  deletedBy: FormControl<CompanyFormRawValue['deletedBy']>;
  industryType: FormControl<CompanyFormRawValue['industryType']>;
  dateOfIncorporation: FormControl<CompanyFormRawValue['dateOfIncorporation']>;
  businessRelationshipPurpose: FormControl<CompanyFormRawValue['businessRelationshipPurpose']>;
  isSanctionsRelated: FormControl<CompanyFormRawValue['isSanctionsRelated']>;
  isAdverseMediaInvolved: FormControl<CompanyFormRawValue['isAdverseMediaInvolved']>;
  employeesQuantity: FormControl<CompanyFormRawValue['employeesQuantity']>;
  cardSpendingAmount: FormControl<CompanyFormRawValue['cardSpendingAmount']>;
  limitDailyPurchase: FormControl<CompanyFormRawValue['limitDailyPurchase']>;
  limitDailyWithdrawal: FormControl<CompanyFormRawValue['limitDailyWithdrawal']>;
  limitMonthlyPurchase: FormControl<CompanyFormRawValue['limitMonthlyPurchase']>;
  limitMonthlyWithdrawal: FormControl<CompanyFormRawValue['limitMonthlyWithdrawal']>;
  kybStatus: FormControl<CompanyFormRawValue['kybStatus']>;
  status: FormControl<CompanyFormRawValue['status']>;
  pushNotificationsEnabled: FormControl<CompanyFormRawValue['pushNotificationsEnabled']>;
  preferredLanguageCode: FormControl<CompanyFormRawValue['preferredLanguageCode']>;
  vatNumber: FormControl<CompanyFormRawValue['vatNumber']>;
};

export type CompanyFormGroup = FormGroup<CompanyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyFormService {
  createCompanyFormGroup(company: CompanyFormGroupInput = { id: null }): CompanyFormGroup {
    const companyRawValue = this.convertCompanyToCompanyRawValue({
      ...this.getFormDefaults(),
      ...company,
    });
    return new FormGroup<CompanyFormGroupContent>({
      id: new FormControl(
        { value: companyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(companyRawValue.name),
      registrationNumber: new FormControl(companyRawValue.registrationNumber),
      regAddressCountryCode: new FormControl(companyRawValue.regAddressCountryCode),
      regAddress1: new FormControl(companyRawValue.regAddress1),
      regAddress2: new FormControl(companyRawValue.regAddress2),
      regAddressCity: new FormControl(companyRawValue.regAddressCity),
      regAddressPostalCode: new FormControl(companyRawValue.regAddressPostalCode),
      hqAddressCountryCode: new FormControl(companyRawValue.hqAddressCountryCode),
      hqAddress1: new FormControl(companyRawValue.hqAddress1),
      hqAddress2: new FormControl(companyRawValue.hqAddress2),
      hqAddressCity: new FormControl(companyRawValue.hqAddressCity),
      hqAddressPostalCode: new FormControl(companyRawValue.hqAddressPostalCode),
      riskProfile: new FormControl(companyRawValue.riskProfile),
      mobile: new FormControl(companyRawValue.mobile),
      email: new FormControl(companyRawValue.email),
      createdAt: new FormControl(companyRawValue.createdAt),
      createdBy: new FormControl(companyRawValue.createdBy),
      updatedAt: new FormControl(companyRawValue.updatedAt),
      updatedBy: new FormControl(companyRawValue.updatedBy),
      deletedAt: new FormControl(companyRawValue.deletedAt),
      deletedBy: new FormControl(companyRawValue.deletedBy),
      industryType: new FormControl(companyRawValue.industryType),
      dateOfIncorporation: new FormControl(companyRawValue.dateOfIncorporation),
      businessRelationshipPurpose: new FormControl(companyRawValue.businessRelationshipPurpose),
      isSanctionsRelated: new FormControl(companyRawValue.isSanctionsRelated),
      isAdverseMediaInvolved: new FormControl(companyRawValue.isAdverseMediaInvolved),
      employeesQuantity: new FormControl(companyRawValue.employeesQuantity),
      cardSpendingAmount: new FormControl(companyRawValue.cardSpendingAmount),
      limitDailyPurchase: new FormControl(companyRawValue.limitDailyPurchase),
      limitDailyWithdrawal: new FormControl(companyRawValue.limitDailyWithdrawal),
      limitMonthlyPurchase: new FormControl(companyRawValue.limitMonthlyPurchase),
      limitMonthlyWithdrawal: new FormControl(companyRawValue.limitMonthlyWithdrawal),
      kybStatus: new FormControl(companyRawValue.kybStatus),
      status: new FormControl(companyRawValue.status),
      pushNotificationsEnabled: new FormControl(companyRawValue.pushNotificationsEnabled),
      preferredLanguageCode: new FormControl(companyRawValue.preferredLanguageCode),
      vatNumber: new FormControl(companyRawValue.vatNumber),
    });
  }

  getCompany(form: CompanyFormGroup): ICompany | NewCompany {
    return this.convertCompanyRawValueToCompany(form.getRawValue() as CompanyFormRawValue | NewCompanyFormRawValue);
  }

  resetForm(form: CompanyFormGroup, company: CompanyFormGroupInput): void {
    const companyRawValue = this.convertCompanyToCompanyRawValue({ ...this.getFormDefaults(), ...company });
    form.reset(
      {
        ...companyRawValue,
        id: { value: companyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CompanyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
      deletedAt: currentTime,
      dateOfIncorporation: currentTime,
      isSanctionsRelated: false,
      isAdverseMediaInvolved: false,
      pushNotificationsEnabled: false,
    };
  }

  private convertCompanyRawValueToCompany(rawCompany: CompanyFormRawValue | NewCompanyFormRawValue): ICompany | NewCompany {
    return {
      ...rawCompany,
      createdAt: dayjs(rawCompany.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawCompany.updatedAt, DATE_TIME_FORMAT),
      deletedAt: dayjs(rawCompany.deletedAt, DATE_TIME_FORMAT),
      dateOfIncorporation: dayjs(rawCompany.dateOfIncorporation, DATE_TIME_FORMAT),
    };
  }

  private convertCompanyToCompanyRawValue(
    company: ICompany | (Partial<NewCompany> & CompanyFormDefaults),
  ): CompanyFormRawValue | PartialWithRequiredKeyOf<NewCompanyFormRawValue> {
    return {
      ...company,
      createdAt: company.createdAt ? company.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: company.updatedAt ? company.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      deletedAt: company.deletedAt ? company.deletedAt.format(DATE_TIME_FORMAT) : undefined,
      dateOfIncorporation: company.dateOfIncorporation ? company.dateOfIncorporation.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
