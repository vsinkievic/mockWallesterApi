import dayjs from 'dayjs/esm';

import { IAccountStatementRecord, NewAccountStatementRecord } from './account-statement-record.model';

export const sampleWithRequiredData: IAccountStatementRecord = {
  id: '69e5809c-2756-4df6-8f8e-38a2ca1d2d6b',
};

export const sampleWithPartialData: IAccountStatementRecord = {
  id: 'eeba1fa3-ca9b-4690-9800-d9fdcc139b17',
  group: 'PaymentEEAFixedFee',
  date: dayjs('2025-04-22T17:05'),
  transactionAmount: 3166.26,
  merchantCity: 'tensely like',
  merchantCountryCode: 'ALA',
  description: 'satirize at',
  originalAuthorizationId: 'oh flat',
  isCleared: false,
  status: 'Pending',
  hasPaymentDocumentFiles: true,
  embossingName: 'colon opposite meh',
  embossingLastName: 'apt tromp',
  purchaseDate: dayjs('2025-04-22T17:40'),
  enrichedMerchantName: 'amidst',
  enrichedMerchantUrl: 'version',
  enrichedMerchantDomain: 'towards',
  totalAmount: 29895.45,
};

export const sampleWithFullData: IAccountStatementRecord = {
  id: '98fe9456-73f1-4d7d-b92a-74ea2aa50a07',
  cardId: 'a9ea7c6b-ccab-4f51-8b79-057aaf344666',
  type: 'AccountAdjustment',
  group: 'CardDeliveryFixedFee',
  date: dayjs('2025-04-22T22:32'),
  transactionAmount: 8328.63,
  transactionCurrencyCode: 'ISK',
  accountAmount: 4391.33,
  accountCurrencyCode: 'EGP',
  merchantCategoryCode: 'MCC7829',
  merchantId: 'opposite',
  terminalId: 'before whoa',
  merchantName: 'afore presell',
  merchantCity: 'disclosure',
  merchantCountryCode: 'ECU',
  description: 'smoothly gee statue',
  originalAuthorizationId: 'carefree nor er',
  isReversal: false,
  isDeclined: true,
  isCleared: true,
  markedForDisputeAt: dayjs('2025-04-23T03:30'),
  markedForDisputeBy: 'ceramic aha effector',
  status: 'Pending',
  response: 'Declined',
  responseCode: 'ha',
  accountExternalId: 'mockingly',
  maskedCardNumber: 'ownership anti orange',
  hasPaymentDocumentFiles: false,
  hasPaymentNotes: false,
  cardName: 'overload gosh damaged',
  embossingName: 'coolly',
  embossingFirstName: 'miskey healthily',
  embossingLastName: 'yet procurement',
  embossingCompanyName: 'before',
  subType: 'lawmaker wash',
  purchaseDate: dayjs('2025-04-22T13:35'),
  exchangeRate: 25097.9,
  enrichedMerchantName: 'yowza question',
  enrichedMerchantUrl: 'inside quiet',
  enrichedMerchantDomain: 'tightly',
  enrichedMerchantTelephone: 'midst',
  enrichedMerchantIconUrl: 'aboard',
  totalAmount: 4189.22,
};

export const sampleWithNewData: NewAccountStatementRecord = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
