import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICard, NewCard } from '../card.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICard for edit and NewCardFormGroupInput for create.
 */
type CardFormGroupInput = ICard | PartialWithRequiredKeyOf<NewCard>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICard | NewCard> = Omit<
  T,
  'expiryDate' | 'blockedAt' | 'activatedAt' | 'createdAt' | 'updatedAt' | 'closedAt' | 'dispatchedAt'
> & {
  expiryDate?: string | null;
  blockedAt?: string | null;
  activatedAt?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  closedAt?: string | null;
  dispatchedAt?: string | null;
};

type CardFormRawValue = FormValueOf<ICard>;

type NewCardFormRawValue = FormValueOf<NewCard>;

type CardFormDefaults = Pick<
  NewCard,
  | 'id'
  | 'expiryDate'
  | 'blockedAt'
  | 'secure3DOutOfBandEnabled'
  | 'isEnrolledFor3DSecure'
  | 'isCard3DSecureActivated'
  | 'renewAutomatically'
  | 'isDisposable'
  | 'securityContactlessEnabled'
  | 'securityWithdrawalEnabled'
  | 'securityInternetPurchaseEnabled'
  | 'securityOverallLimitsEnabled'
  | 'securityAllTimeLimitsEnabled'
  | 'activatedAt'
  | 'createdAt'
  | 'updatedAt'
  | 'closedAt'
  | 'dispatchedAt'
  | 'notificationReceiptsReminderEnabled'
  | 'notificationInstantSpendUpdateEnabled'
>;

type CardFormGroupContent = {
  id: FormControl<CardFormRawValue['id'] | NewCard['id']>;
  predecessorCardId: FormControl<CardFormRawValue['predecessorCardId']>;
  accountId: FormControl<CardFormRawValue['accountId']>;
  personId: FormControl<CardFormRawValue['personId']>;
  externalId: FormControl<CardFormRawValue['externalId']>;
  type: FormControl<CardFormRawValue['type']>;
  name: FormControl<CardFormRawValue['name']>;
  maskedCardNumber: FormControl<CardFormRawValue['maskedCardNumber']>;
  referenceNumber: FormControl<CardFormRawValue['referenceNumber']>;
  expiryDate: FormControl<CardFormRawValue['expiryDate']>;
  blockType: FormControl<CardFormRawValue['blockType']>;
  blockedAt: FormControl<CardFormRawValue['blockedAt']>;
  blockedBy: FormControl<CardFormRawValue['blockedBy']>;
  status: FormControl<CardFormRawValue['status']>;
  embossingName: FormControl<CardFormRawValue['embossingName']>;
  embossingFirstName: FormControl<CardFormRawValue['embossingFirstName']>;
  embossingLastName: FormControl<CardFormRawValue['embossingLastName']>;
  embossingCompanyName: FormControl<CardFormRawValue['embossingCompanyName']>;
  limitDailyPurchase: FormControl<CardFormRawValue['limitDailyPurchase']>;
  limitDailyWithdrawal: FormControl<CardFormRawValue['limitDailyWithdrawal']>;
  limitMonthlyPurchase: FormControl<CardFormRawValue['limitMonthlyPurchase']>;
  limitMonthlyWithdrawal: FormControl<CardFormRawValue['limitMonthlyWithdrawal']>;
  limitTransactionPurchase: FormControl<CardFormRawValue['limitTransactionPurchase']>;
  secure3DType: FormControl<CardFormRawValue['secure3DType']>;
  secure3DMobile: FormControl<CardFormRawValue['secure3DMobile']>;
  secure3DEmail: FormControl<CardFormRawValue['secure3DEmail']>;
  secure3DLanguageCode: FormControl<CardFormRawValue['secure3DLanguageCode']>;
  secure3DOutOfBandEnabled: FormControl<CardFormRawValue['secure3DOutOfBandEnabled']>;
  secure3DOutOfBandId: FormControl<CardFormRawValue['secure3DOutOfBandId']>;
  deliveryFirstName: FormControl<CardFormRawValue['deliveryFirstName']>;
  deliveryLastName: FormControl<CardFormRawValue['deliveryLastName']>;
  deliveryCompanyName: FormControl<CardFormRawValue['deliveryCompanyName']>;
  deliveryAddress1: FormControl<CardFormRawValue['deliveryAddress1']>;
  deliveryAddress2: FormControl<CardFormRawValue['deliveryAddress2']>;
  deliveryPostalCode: FormControl<CardFormRawValue['deliveryPostalCode']>;
  deliveryCity: FormControl<CardFormRawValue['deliveryCity']>;
  deliveryCountryCode: FormControl<CardFormRawValue['deliveryCountryCode']>;
  deliveryDispatchMethod: FormControl<CardFormRawValue['deliveryDispatchMethod']>;
  deliveryPhone: FormControl<CardFormRawValue['deliveryPhone']>;
  deliveryTrackingNumber: FormControl<CardFormRawValue['deliveryTrackingNumber']>;
  isEnrolledFor3DSecure: FormControl<CardFormRawValue['isEnrolledFor3DSecure']>;
  isCard3DSecureActivated: FormControl<CardFormRawValue['isCard3DSecureActivated']>;
  renewAutomatically: FormControl<CardFormRawValue['renewAutomatically']>;
  isDisposable: FormControl<CardFormRawValue['isDisposable']>;
  securityContactlessEnabled: FormControl<CardFormRawValue['securityContactlessEnabled']>;
  securityWithdrawalEnabled: FormControl<CardFormRawValue['securityWithdrawalEnabled']>;
  securityInternetPurchaseEnabled: FormControl<CardFormRawValue['securityInternetPurchaseEnabled']>;
  securityOverallLimitsEnabled: FormControl<CardFormRawValue['securityOverallLimitsEnabled']>;
  securityAllTimeLimitsEnabled: FormControl<CardFormRawValue['securityAllTimeLimitsEnabled']>;
  personalizationProductCode: FormControl<CardFormRawValue['personalizationProductCode']>;
  carrierType: FormControl<CardFormRawValue['carrierType']>;
  cardMetadataProfileId: FormControl<CardFormRawValue['cardMetadataProfileId']>;
  activatedAt: FormControl<CardFormRawValue['activatedAt']>;
  createdAt: FormControl<CardFormRawValue['createdAt']>;
  updatedAt: FormControl<CardFormRawValue['updatedAt']>;
  closedAt: FormControl<CardFormRawValue['closedAt']>;
  closedBy: FormControl<CardFormRawValue['closedBy']>;
  closeReason: FormControl<CardFormRawValue['closeReason']>;
  companyId: FormControl<CardFormRawValue['companyId']>;
  dispatchedAt: FormControl<CardFormRawValue['dispatchedAt']>;
  notificationReceiptsReminderEnabled: FormControl<CardFormRawValue['notificationReceiptsReminderEnabled']>;
  notificationInstantSpendUpdateEnabled: FormControl<CardFormRawValue['notificationInstantSpendUpdateEnabled']>;
  disposableType: FormControl<CardFormRawValue['disposableType']>;
};

export type CardFormGroup = FormGroup<CardFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CardFormService {
  createCardFormGroup(card: CardFormGroupInput = { id: null }): CardFormGroup {
    const cardRawValue = this.convertCardToCardRawValue({
      ...this.getFormDefaults(),
      ...card,
    });
    return new FormGroup<CardFormGroupContent>({
      id: new FormControl(
        { value: cardRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      predecessorCardId: new FormControl(cardRawValue.predecessorCardId),
      accountId: new FormControl(cardRawValue.accountId),
      personId: new FormControl(cardRawValue.personId),
      externalId: new FormControl(cardRawValue.externalId),
      type: new FormControl(cardRawValue.type),
      name: new FormControl(cardRawValue.name),
      maskedCardNumber: new FormControl(cardRawValue.maskedCardNumber),
      referenceNumber: new FormControl(cardRawValue.referenceNumber),
      expiryDate: new FormControl(cardRawValue.expiryDate),
      blockType: new FormControl(cardRawValue.blockType),
      blockedAt: new FormControl(cardRawValue.blockedAt),
      blockedBy: new FormControl(cardRawValue.blockedBy),
      status: new FormControl(cardRawValue.status),
      embossingName: new FormControl(cardRawValue.embossingName),
      embossingFirstName: new FormControl(cardRawValue.embossingFirstName),
      embossingLastName: new FormControl(cardRawValue.embossingLastName),
      embossingCompanyName: new FormControl(cardRawValue.embossingCompanyName),
      limitDailyPurchase: new FormControl(cardRawValue.limitDailyPurchase),
      limitDailyWithdrawal: new FormControl(cardRawValue.limitDailyWithdrawal),
      limitMonthlyPurchase: new FormControl(cardRawValue.limitMonthlyPurchase),
      limitMonthlyWithdrawal: new FormControl(cardRawValue.limitMonthlyWithdrawal),
      limitTransactionPurchase: new FormControl(cardRawValue.limitTransactionPurchase),
      secure3DType: new FormControl(cardRawValue.secure3DType),
      secure3DMobile: new FormControl(cardRawValue.secure3DMobile),
      secure3DEmail: new FormControl(cardRawValue.secure3DEmail),
      secure3DLanguageCode: new FormControl(cardRawValue.secure3DLanguageCode),
      secure3DOutOfBandEnabled: new FormControl(cardRawValue.secure3DOutOfBandEnabled),
      secure3DOutOfBandId: new FormControl(cardRawValue.secure3DOutOfBandId),
      deliveryFirstName: new FormControl(cardRawValue.deliveryFirstName),
      deliveryLastName: new FormControl(cardRawValue.deliveryLastName),
      deliveryCompanyName: new FormControl(cardRawValue.deliveryCompanyName),
      deliveryAddress1: new FormControl(cardRawValue.deliveryAddress1),
      deliveryAddress2: new FormControl(cardRawValue.deliveryAddress2),
      deliveryPostalCode: new FormControl(cardRawValue.deliveryPostalCode),
      deliveryCity: new FormControl(cardRawValue.deliveryCity),
      deliveryCountryCode: new FormControl(cardRawValue.deliveryCountryCode),
      deliveryDispatchMethod: new FormControl(cardRawValue.deliveryDispatchMethod),
      deliveryPhone: new FormControl(cardRawValue.deliveryPhone),
      deliveryTrackingNumber: new FormControl(cardRawValue.deliveryTrackingNumber),
      isEnrolledFor3DSecure: new FormControl(cardRawValue.isEnrolledFor3DSecure),
      isCard3DSecureActivated: new FormControl(cardRawValue.isCard3DSecureActivated),
      renewAutomatically: new FormControl(cardRawValue.renewAutomatically),
      isDisposable: new FormControl(cardRawValue.isDisposable),
      securityContactlessEnabled: new FormControl(cardRawValue.securityContactlessEnabled),
      securityWithdrawalEnabled: new FormControl(cardRawValue.securityWithdrawalEnabled),
      securityInternetPurchaseEnabled: new FormControl(cardRawValue.securityInternetPurchaseEnabled),
      securityOverallLimitsEnabled: new FormControl(cardRawValue.securityOverallLimitsEnabled),
      securityAllTimeLimitsEnabled: new FormControl(cardRawValue.securityAllTimeLimitsEnabled),
      personalizationProductCode: new FormControl(cardRawValue.personalizationProductCode),
      carrierType: new FormControl(cardRawValue.carrierType),
      cardMetadataProfileId: new FormControl(cardRawValue.cardMetadataProfileId),
      activatedAt: new FormControl(cardRawValue.activatedAt),
      createdAt: new FormControl(cardRawValue.createdAt),
      updatedAt: new FormControl(cardRawValue.updatedAt),
      closedAt: new FormControl(cardRawValue.closedAt),
      closedBy: new FormControl(cardRawValue.closedBy),
      closeReason: new FormControl(cardRawValue.closeReason),
      companyId: new FormControl(cardRawValue.companyId),
      dispatchedAt: new FormControl(cardRawValue.dispatchedAt),
      notificationReceiptsReminderEnabled: new FormControl(cardRawValue.notificationReceiptsReminderEnabled),
      notificationInstantSpendUpdateEnabled: new FormControl(cardRawValue.notificationInstantSpendUpdateEnabled),
      disposableType: new FormControl(cardRawValue.disposableType),
    });
  }

  getCard(form: CardFormGroup): ICard | NewCard {
    return this.convertCardRawValueToCard(form.getRawValue() as CardFormRawValue | NewCardFormRawValue);
  }

  resetForm(form: CardFormGroup, card: CardFormGroupInput): void {
    const cardRawValue = this.convertCardToCardRawValue({ ...this.getFormDefaults(), ...card });
    form.reset(
      {
        ...cardRawValue,
        id: { value: cardRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CardFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      expiryDate: currentTime,
      blockedAt: currentTime,
      secure3DOutOfBandEnabled: false,
      isEnrolledFor3DSecure: false,
      isCard3DSecureActivated: false,
      renewAutomatically: false,
      isDisposable: false,
      securityContactlessEnabled: false,
      securityWithdrawalEnabled: false,
      securityInternetPurchaseEnabled: false,
      securityOverallLimitsEnabled: false,
      securityAllTimeLimitsEnabled: false,
      activatedAt: currentTime,
      createdAt: currentTime,
      updatedAt: currentTime,
      closedAt: currentTime,
      dispatchedAt: currentTime,
      notificationReceiptsReminderEnabled: false,
      notificationInstantSpendUpdateEnabled: false,
    };
  }

  private convertCardRawValueToCard(rawCard: CardFormRawValue | NewCardFormRawValue): ICard | NewCard {
    return {
      ...rawCard,
      expiryDate: dayjs(rawCard.expiryDate, DATE_TIME_FORMAT),
      blockedAt: dayjs(rawCard.blockedAt, DATE_TIME_FORMAT),
      activatedAt: dayjs(rawCard.activatedAt, DATE_TIME_FORMAT),
      createdAt: dayjs(rawCard.createdAt, DATE_TIME_FORMAT),
      updatedAt: dayjs(rawCard.updatedAt, DATE_TIME_FORMAT),
      closedAt: dayjs(rawCard.closedAt, DATE_TIME_FORMAT),
      dispatchedAt: dayjs(rawCard.dispatchedAt, DATE_TIME_FORMAT),
    };
  }

  private convertCardToCardRawValue(
    card: ICard | (Partial<NewCard> & CardFormDefaults),
  ): CardFormRawValue | PartialWithRequiredKeyOf<NewCardFormRawValue> {
    return {
      ...card,
      expiryDate: card.expiryDate ? card.expiryDate.format(DATE_TIME_FORMAT) : undefined,
      blockedAt: card.blockedAt ? card.blockedAt.format(DATE_TIME_FORMAT) : undefined,
      activatedAt: card.activatedAt ? card.activatedAt.format(DATE_TIME_FORMAT) : undefined,
      createdAt: card.createdAt ? card.createdAt.format(DATE_TIME_FORMAT) : undefined,
      updatedAt: card.updatedAt ? card.updatedAt.format(DATE_TIME_FORMAT) : undefined,
      closedAt: card.closedAt ? card.closedAt.format(DATE_TIME_FORMAT) : undefined,
      dispatchedAt: card.dispatchedAt ? card.dispatchedAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
