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
type FormValueOf<T extends ICardAccount | NewCardAccount> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

type CardAccountFormRawValue = FormValueOf<ICardAccount>;

type NewCardAccountFormRawValue = FormValueOf<NewCardAccount>;

type CardAccountFormDefaults = Pick<NewCardAccount, 'id' | 'createdAt' | 'updatedAt'>;

type CardAccountFormGroupContent = {
  id: FormControl<CardAccountFormRawValue['id'] | NewCardAccount['id']>;
  accountNumber: FormControl<CardAccountFormRawValue['accountNumber']>;
  currency: FormControl<CardAccountFormRawValue['currency']>;
  balance: FormControl<CardAccountFormRawValue['balance']>;
  reservedAmount: FormControl<CardAccountFormRawValue['reservedAmount']>;
  availableAmount: FormControl<CardAccountFormRawValue['availableAmount']>;
  blockedAmount: FormControl<CardAccountFormRawValue['blockedAmount']>;
  status: FormControl<CardAccountFormRawValue['status']>;
  createdAt: FormControl<CardAccountFormRawValue['createdAt']>;
  updatedAt: FormControl<CardAccountFormRawValue['updatedAt']>;
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
      accountNumber: new FormControl(cardAccountRawValue.accountNumber, {
        validators: [Validators.required],
      }),
      currency: new FormControl(cardAccountRawValue.currency, {
        validators: [Validators.required],
      }),
      balance: new FormControl(cardAccountRawValue.balance, {
        validators: [Validators.required],
      }),
      reservedAmount: new FormControl(cardAccountRawValue.reservedAmount, {
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
    };
  }

  private convertCardAccountToCardAccountRawValue(
    cardAccount: ICardAccount | (Partial<NewCardAccount> & CardAccountFormDefaults),
  ): CardAccountFormRawValue | PartialWithRequiredKeyOf<NewCardAccountFormRawValue> {
    return {
      ...cardAccount,
      createdAt: cardAccount.createdAt ? cardAccount.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: cardAccount.updatedAt ? cardAccount.updatedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
