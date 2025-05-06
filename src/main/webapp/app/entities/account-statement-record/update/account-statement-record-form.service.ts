import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAccountStatementRecord, NewAccountStatementRecord } from '../account-statement-record.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccountStatementRecord for edit and NewAccountStatementRecordFormGroupInput for create.
 */
type AccountStatementRecordFormGroupInput = IAccountStatementRecord | PartialWithRequiredKeyOf<NewAccountStatementRecord>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAccountStatementRecord | NewAccountStatementRecord> = Omit<
  T,
  'date' | 'markedForDisputeAt' | 'purchaseDate'
> & {
  date?: string | null;
  markedForDisputeAt?: string | null;
  purchaseDate?: string | null;
};

type AccountStatementRecordFormRawValue = FormValueOf<IAccountStatementRecord>;

type NewAccountStatementRecordFormRawValue = FormValueOf<NewAccountStatementRecord>;

type AccountStatementRecordFormDefaults = Pick<
  NewAccountStatementRecord,
  | 'id'
  | 'date'
  | 'isReversal'
  | 'isDeclined'
  | 'isCleared'
  | 'markedForDisputeAt'
  | 'hasPaymentDocumentFiles'
  | 'hasPaymentNotes'
  | 'purchaseDate'
>;

type AccountStatementRecordFormGroupContent = {
  id: FormControl<AccountStatementRecordFormRawValue['id'] | NewAccountStatementRecord['id']>;
  accountId: FormControl<AccountStatementRecordFormRawValue['accountId']>;
  cardId: FormControl<AccountStatementRecordFormRawValue['cardId']>;
  type: FormControl<AccountStatementRecordFormRawValue['type']>;
  group: FormControl<AccountStatementRecordFormRawValue['group']>;
  date: FormControl<AccountStatementRecordFormRawValue['date']>;
  transactionAmount: FormControl<AccountStatementRecordFormRawValue['transactionAmount']>;
  transactionCurrencyCode: FormControl<AccountStatementRecordFormRawValue['transactionCurrencyCode']>;
  accountAmount: FormControl<AccountStatementRecordFormRawValue['accountAmount']>;
  accountCurrencyCode: FormControl<AccountStatementRecordFormRawValue['accountCurrencyCode']>;
  merchantCategoryCode: FormControl<AccountStatementRecordFormRawValue['merchantCategoryCode']>;
  merchantId: FormControl<AccountStatementRecordFormRawValue['merchantId']>;
  terminalId: FormControl<AccountStatementRecordFormRawValue['terminalId']>;
  merchantName: FormControl<AccountStatementRecordFormRawValue['merchantName']>;
  merchantCity: FormControl<AccountStatementRecordFormRawValue['merchantCity']>;
  merchantCountryCode: FormControl<AccountStatementRecordFormRawValue['merchantCountryCode']>;
  description: FormControl<AccountStatementRecordFormRawValue['description']>;
  originalAuthorizationId: FormControl<AccountStatementRecordFormRawValue['originalAuthorizationId']>;
  isReversal: FormControl<AccountStatementRecordFormRawValue['isReversal']>;
  isDeclined: FormControl<AccountStatementRecordFormRawValue['isDeclined']>;
  isCleared: FormControl<AccountStatementRecordFormRawValue['isCleared']>;
  markedForDisputeAt: FormControl<AccountStatementRecordFormRawValue['markedForDisputeAt']>;
  markedForDisputeBy: FormControl<AccountStatementRecordFormRawValue['markedForDisputeBy']>;
  status: FormControl<AccountStatementRecordFormRawValue['status']>;
  response: FormControl<AccountStatementRecordFormRawValue['response']>;
  responseCode: FormControl<AccountStatementRecordFormRawValue['responseCode']>;
  hasPaymentDocumentFiles: FormControl<AccountStatementRecordFormRawValue['hasPaymentDocumentFiles']>;
  hasPaymentNotes: FormControl<AccountStatementRecordFormRawValue['hasPaymentNotes']>;
  subType: FormControl<AccountStatementRecordFormRawValue['subType']>;
  purchaseDate: FormControl<AccountStatementRecordFormRawValue['purchaseDate']>;
  exchangeRate: FormControl<AccountStatementRecordFormRawValue['exchangeRate']>;
  enrichedMerchantName: FormControl<AccountStatementRecordFormRawValue['enrichedMerchantName']>;
  enrichedMerchantUrl: FormControl<AccountStatementRecordFormRawValue['enrichedMerchantUrl']>;
  enrichedMerchantDomain: FormControl<AccountStatementRecordFormRawValue['enrichedMerchantDomain']>;
  enrichedMerchantTelephone: FormControl<AccountStatementRecordFormRawValue['enrichedMerchantTelephone']>;
  enrichedMerchantIconUrl: FormControl<AccountStatementRecordFormRawValue['enrichedMerchantIconUrl']>;
  totalAmount: FormControl<AccountStatementRecordFormRawValue['totalAmount']>;
};

export type AccountStatementRecordFormGroup = FormGroup<AccountStatementRecordFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccountStatementRecordFormService {
  createAccountStatementRecordFormGroup(
    accountStatementRecord: AccountStatementRecordFormGroupInput = { id: null },
  ): AccountStatementRecordFormGroup {
    const accountStatementRecordRawValue = this.convertAccountStatementRecordToAccountStatementRecordRawValue({
      ...this.getFormDefaults(),
      ...accountStatementRecord,
    });
    return new FormGroup<AccountStatementRecordFormGroupContent>({
      id: new FormControl(
        { value: accountStatementRecordRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      accountId: new FormControl(accountStatementRecordRawValue.accountId),
      cardId: new FormControl(accountStatementRecordRawValue.cardId),
      type: new FormControl(accountStatementRecordRawValue.type),
      group: new FormControl(accountStatementRecordRawValue.group),
      date: new FormControl(accountStatementRecordRawValue.date),
      transactionAmount: new FormControl(accountStatementRecordRawValue.transactionAmount),
      transactionCurrencyCode: new FormControl(accountStatementRecordRawValue.transactionCurrencyCode),
      accountAmount: new FormControl(accountStatementRecordRawValue.accountAmount),
      accountCurrencyCode: new FormControl(accountStatementRecordRawValue.accountCurrencyCode),
      merchantCategoryCode: new FormControl(accountStatementRecordRawValue.merchantCategoryCode),
      merchantId: new FormControl(accountStatementRecordRawValue.merchantId),
      terminalId: new FormControl(accountStatementRecordRawValue.terminalId),
      merchantName: new FormControl(accountStatementRecordRawValue.merchantName),
      merchantCity: new FormControl(accountStatementRecordRawValue.merchantCity),
      merchantCountryCode: new FormControl(accountStatementRecordRawValue.merchantCountryCode),
      description: new FormControl(accountStatementRecordRawValue.description),
      originalAuthorizationId: new FormControl(accountStatementRecordRawValue.originalAuthorizationId),
      isReversal: new FormControl(accountStatementRecordRawValue.isReversal),
      isDeclined: new FormControl(accountStatementRecordRawValue.isDeclined),
      isCleared: new FormControl(accountStatementRecordRawValue.isCleared),
      markedForDisputeAt: new FormControl(accountStatementRecordRawValue.markedForDisputeAt),
      markedForDisputeBy: new FormControl(accountStatementRecordRawValue.markedForDisputeBy),
      status: new FormControl(accountStatementRecordRawValue.status),
      response: new FormControl(accountStatementRecordRawValue.response),
      responseCode: new FormControl(accountStatementRecordRawValue.responseCode),
      hasPaymentDocumentFiles: new FormControl(accountStatementRecordRawValue.hasPaymentDocumentFiles),
      hasPaymentNotes: new FormControl(accountStatementRecordRawValue.hasPaymentNotes),
      subType: new FormControl(accountStatementRecordRawValue.subType),
      purchaseDate: new FormControl(accountStatementRecordRawValue.purchaseDate),
      exchangeRate: new FormControl(accountStatementRecordRawValue.exchangeRate),
      enrichedMerchantName: new FormControl(accountStatementRecordRawValue.enrichedMerchantName),
      enrichedMerchantUrl: new FormControl(accountStatementRecordRawValue.enrichedMerchantUrl),
      enrichedMerchantDomain: new FormControl(accountStatementRecordRawValue.enrichedMerchantDomain),
      enrichedMerchantTelephone: new FormControl(accountStatementRecordRawValue.enrichedMerchantTelephone),
      enrichedMerchantIconUrl: new FormControl(accountStatementRecordRawValue.enrichedMerchantIconUrl),
      totalAmount: new FormControl(accountStatementRecordRawValue.totalAmount),
    });
  }

  getAccountStatementRecord(form: AccountStatementRecordFormGroup): IAccountStatementRecord | NewAccountStatementRecord {
    return this.convertAccountStatementRecordRawValueToAccountStatementRecord(
      form.getRawValue() as AccountStatementRecordFormRawValue | NewAccountStatementRecordFormRawValue,
    );
  }

  resetForm(form: AccountStatementRecordFormGroup, accountStatementRecord: AccountStatementRecordFormGroupInput): void {
    const accountStatementRecordRawValue = this.convertAccountStatementRecordToAccountStatementRecordRawValue({
      ...this.getFormDefaults(),
      ...accountStatementRecord,
    });
    form.reset(
      {
        ...accountStatementRecordRawValue,
        id: { value: accountStatementRecordRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AccountStatementRecordFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      date: currentTime,
      isReversal: false,
      isDeclined: false,
      isCleared: false,
      markedForDisputeAt: currentTime,
      hasPaymentDocumentFiles: false,
      hasPaymentNotes: false,
      purchaseDate: currentTime,
    };
  }

  private convertAccountStatementRecordRawValueToAccountStatementRecord(
    rawAccountStatementRecord: AccountStatementRecordFormRawValue | NewAccountStatementRecordFormRawValue,
  ): IAccountStatementRecord | NewAccountStatementRecord {
    return {
      ...rawAccountStatementRecord,
      date: dayjs(rawAccountStatementRecord.date, DATE_TIME_FORMAT),
      markedForDisputeAt: dayjs(rawAccountStatementRecord.markedForDisputeAt, DATE_TIME_FORMAT),
      purchaseDate: dayjs(rawAccountStatementRecord.purchaseDate, DATE_TIME_FORMAT),
    };
  }

  private convertAccountStatementRecordToAccountStatementRecordRawValue(
    accountStatementRecord: IAccountStatementRecord | (Partial<NewAccountStatementRecord> & AccountStatementRecordFormDefaults),
  ): AccountStatementRecordFormRawValue | PartialWithRequiredKeyOf<NewAccountStatementRecordFormRawValue> {
    return {
      ...accountStatementRecord,
      date: accountStatementRecord.date ? accountStatementRecord.date.format(DATE_TIME_FORMAT) : undefined,
      markedForDisputeAt: accountStatementRecord.markedForDisputeAt
        ? accountStatementRecord.markedForDisputeAt.format(DATE_TIME_FORMAT)
        : undefined,
      purchaseDate: accountStatementRecord.purchaseDate ? accountStatementRecord.purchaseDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
