import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICardAccount, NewCardAccount } from '../card-account.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICardAccount for edit and NewCardAccountFormGroupInput for create.
 */
type CardAccountFormGroupInput = ICardAccount | PartialWithRequiredKeyOf<NewCardAccount>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICardAccount | NewCardAccount> = Omit<T, 'createdAt' | 'updatedAt' | 'closedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
  closedAt?: string | null;
};

type CardAccountFormRawValue = FormValueOf<ICardAccount>;

type NewCardAccountFormRawValue = FormValueOf<NewCardAccount>;

type CardAccountFormDefaults = Pick<NewCardAccount, 'id' | 'createdAt' | 'updatedAt'>;

type CardAccountFormGroupContent = {
  id: FormControl<CardAccountFormRawValue['id'] | NewCardAccount['id']>;
  currencyCode: FormControl<CardAccountFormRawValue['currencyCode']>;
  balance: FormControl<CardAccountFormRawValue['balance']>;
  availableAmount: FormControl<CardAccountFormRawValue['availableAmount']>;
  blockedAmount: FormControl<CardAccountFormRawValue['blockedAmount']>;
  status: FormControl<CardAccountFormRawValue['status']>;
  createdAt: FormControl<CardAccountFormRawValue['createdAt']>;
  updatedAt: FormControl<CardAccountFormRawValue['updatedAt']>;

  // Additional fields
  cardsCount: FormControl<CardAccountFormRawValue['cardsCount']>;
  closeReason: FormControl<CardAccountFormRawValue['closeReason']>;
  closedAt: FormControl<CardAccountFormRawValue['closedAt']>;
  closedBy: FormControl<CardAccountFormRawValue['closedBy']>;
  companyId: FormControl<CardAccountFormRawValue['companyId']>;
  creditLimit: FormControl<CardAccountFormRawValue['creditLimit']>;
  externalId: FormControl<CardAccountFormRawValue['externalId']>;
  isMain: FormControl<CardAccountFormRawValue['isMain']>;
  name: FormControl<CardAccountFormRawValue['name']>;
  personId: FormControl<CardAccountFormRawValue['personId']>;
  productId: FormControl<CardAccountFormRawValue['productId']>;
  referenceNumber: FormControl<CardAccountFormRawValue['referenceNumber']>;
  usedCredit: FormControl<CardAccountFormRawValue['usedCredit']>;
  viban: FormControl<CardAccountFormRawValue['viban']>;

  // Limits
  dailyContactlessPurchase: FormControl<CardAccountFormRawValue['dailyContactlessPurchase']>;
  dailyInternetPurchase: FormControl<CardAccountFormRawValue['dailyInternetPurchase']>;
  dailyPurchase: FormControl<CardAccountFormRawValue['dailyPurchase']>;
  dailyWithdrawal: FormControl<CardAccountFormRawValue['dailyWithdrawal']>;
  monthlyContactlessPurchase: FormControl<CardAccountFormRawValue['monthlyContactlessPurchase']>;
  monthlyInternetPurchase: FormControl<CardAccountFormRawValue['monthlyInternetPurchase']>;
  monthlyPurchase: FormControl<CardAccountFormRawValue['monthlyPurchase']>;
  monthlyWithdrawal: FormControl<CardAccountFormRawValue['monthlyWithdrawal']>;
  weeklyContactlessPurchase: FormControl<CardAccountFormRawValue['weeklyContactlessPurchase']>;
  weeklyInternetPurchase: FormControl<CardAccountFormRawValue['weeklyInternetPurchase']>;
  weeklyPurchase: FormControl<CardAccountFormRawValue['weeklyPurchase']>;
  weeklyWithdrawal: FormControl<CardAccountFormRawValue['weeklyWithdrawal']>;

  // Top-up details
  bankAddress: FormControl<CardAccountFormRawValue['bankAddress']>;
  bankName: FormControl<CardAccountFormRawValue['bankName']>;
  iban: FormControl<CardAccountFormRawValue['iban']>;
  paymentDetails: FormControl<CardAccountFormRawValue['paymentDetails']>;
  receiverName: FormControl<CardAccountFormRawValue['receiverName']>;
  registrationNumber: FormControl<CardAccountFormRawValue['registrationNumber']>;
  swiftCode: FormControl<CardAccountFormRawValue['swiftCode']>;
};

export type CardAccountFormGroup = FormGroup<CardAccountFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CardAccountFormService {
  createCardAccountFormGroup(cardAccount: CardAccountFormGroupInput = { id: null }): CardAccountFormGroup {
    const cardAccountRawValue = this.convertCardAccountToCardAccountRawValue({
      ...this.getFormDefaults(),
      ...cardAccount,
    });
    return new FormGroup<CardAccountFormGroupContent>({
      id: new FormControl(
        { value: cardAccountRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      currencyCode: new FormControl(cardAccountRawValue.currencyCode, {
        validators: [Validators.required],
      }),
      balance: new FormControl(cardAccountRawValue.balance, {
        validators: [Validators.required],
      }),
      availableAmount: new FormControl(cardAccountRawValue.availableAmount, {
        validators: [Validators.required],
      }),
      blockedAmount: new FormControl(cardAccountRawValue.blockedAmount, {
        validators: [Validators.required],
      }),
      status: new FormControl(cardAccountRawValue.status, {
        validators: [Validators.required],
      }),
      createdAt: new FormControl(cardAccountRawValue.createdAt, {
        validators: [Validators.required],
      }),
      updatedAt: new FormControl(cardAccountRawValue.updatedAt, {
        validators: [Validators.required],
      }),

      // Additional fields
      cardsCount: new FormControl(cardAccountRawValue.cardsCount),
      closeReason: new FormControl(cardAccountRawValue.closeReason),
      closedAt: new FormControl(cardAccountRawValue.closedAt),
      closedBy: new FormControl(cardAccountRawValue.closedBy),
      companyId: new FormControl(cardAccountRawValue.companyId),
      creditLimit: new FormControl(cardAccountRawValue.creditLimit),
      externalId: new FormControl(cardAccountRawValue.externalId),
      isMain: new FormControl(cardAccountRawValue.isMain),
      name: new FormControl(cardAccountRawValue.name),
      personId: new FormControl(cardAccountRawValue.personId),
      productId: new FormControl(cardAccountRawValue.productId),
      referenceNumber: new FormControl(cardAccountRawValue.referenceNumber),
      usedCredit: new FormControl(cardAccountRawValue.usedCredit),
      viban: new FormControl(cardAccountRawValue.viban),

      // Limits
      dailyContactlessPurchase: new FormControl(cardAccountRawValue.dailyContactlessPurchase),
      dailyInternetPurchase: new FormControl(cardAccountRawValue.dailyInternetPurchase),
      dailyPurchase: new FormControl(cardAccountRawValue.dailyPurchase),
      dailyWithdrawal: new FormControl(cardAccountRawValue.dailyWithdrawal),
      monthlyContactlessPurchase: new FormControl(cardAccountRawValue.monthlyContactlessPurchase),
      monthlyInternetPurchase: new FormControl(cardAccountRawValue.monthlyInternetPurchase),
      monthlyPurchase: new FormControl(cardAccountRawValue.monthlyPurchase),
      monthlyWithdrawal: new FormControl(cardAccountRawValue.monthlyWithdrawal),
      weeklyContactlessPurchase: new FormControl(cardAccountRawValue.weeklyContactlessPurchase),
      weeklyInternetPurchase: new FormControl(cardAccountRawValue.weeklyInternetPurchase),
      weeklyPurchase: new FormControl(cardAccountRawValue.weeklyPurchase),
      weeklyWithdrawal: new FormControl(cardAccountRawValue.weeklyWithdrawal),

      // Top-up details
      bankAddress: new FormControl(cardAccountRawValue.bankAddress),
      bankName: new FormControl(cardAccountRawValue.bankName),
      iban: new FormControl(cardAccountRawValue.iban),
      paymentDetails: new FormControl(cardAccountRawValue.paymentDetails),
      receiverName: new FormControl(cardAccountRawValue.receiverName),
      registrationNumber: new FormControl(cardAccountRawValue.registrationNumber),
      swiftCode: new FormControl(cardAccountRawValue.swiftCode),
    });
  }

  getCardAccount(form: CardAccountFormGroup): ICardAccount | NewCardAccount {
    return this.convertCardAccountRawValueToCardAccount(form.getRawValue() as CardAccountFormRawValue | NewCardAccountFormRawValue);
  }

  resetForm(form: CardAccountFormGroup, cardAccount: CardAccountFormGroupInput): void {
    const cardAccountRawValue = this.convertCardAccountToCardAccountRawValue({ ...this.getFormDefaults(), ...cardAccount });
    form.reset(
      {
        ...cardAccountRawValue,
        id: { value: cardAccountRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CardAccountFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
      updatedAt: currentTime,
    };
  }

  private convertCardAccountRawValueToCardAccount(
    rawCardAccount: CardAccountFormRawValue | NewCardAccountFormRawValue,
  ): ICardAccount | NewCardAccount {
    return {
      ...rawCardAccount,
      createdAt: dayjs(rawCardAccount.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawCardAccount.updatedAt, DATE_TIME_FORMAT),
      closedAt: rawCardAccount.closedAt ? dayjs(rawCardAccount.closedAt, DATE_TIME_FORMAT) : undefined,
    };
  }

  private convertCardAccountToCardAccountRawValue(
    cardAccount: ICardAccount | (Partial<NewCardAccount> & CardAccountFormDefaults),
  ): CardAccountFormRawValue | PartialWithRequiredKeyOf<NewCardAccountFormRawValue> {
    return {
      ...cardAccount,
      createdAt: cardAccount.createdAt ? cardAccount.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: cardAccount.updatedAt ? cardAccount.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      closedAt: cardAccount.closedAt ? cardAccount.closedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
