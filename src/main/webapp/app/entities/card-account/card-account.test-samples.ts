import dayjs from 'dayjs/esm';
import { CurrencyCode } from 'app/entities/enumerations/currency-code.model';
import { AccountStatus } from 'app/entities/enumerations/account-status.model';
import { ICardAccount, NewCardAccount } from './card-account.model';

export const sampleWithRequiredData: ICardAccount = {
  id: '5a9347f5-d90a-4408-8cc5-b5d9808d587e',
  name: 'impact stunning',
  externalId: 'URE202401001A',
  currencyCode: 'EUR',
  balance: 15000.0,
  blockedAmount: 5000.0,
  availableAmount: 10000.0,
  status: 'Active',
  createdAt: dayjs('2025-04-23T00:06:28'),
  updatedAt: dayjs('2025-04-22T21:54:44'),
};

export const sampleWithPartialData: ICardAccount = {
  id: 'e463c913-8815-4d38-b730-8a9bf16c0100',
  name: 'joy',
  externalId: 'URE202401002B',
  currencyCode: 'EUR',
  balance: 20000.0,
  blockedAmount: 8000.0,
  availableAmount: 12000.0,
  status: 'Active',
  createdAt: dayjs('2025-04-22T17:31:21'),
  updatedAt: dayjs('2025-04-22T22:12:02'),
  companyId: '40ad3604-acda-4738-aae8-79752b63e6f5',
  creditLimit: 25000.0,
  usedCredit: 5000.0,
  isMain: true,
};

export const sampleWithFullData: ICardAccount = {
  id: 'a9e9eb2f-f233-4c33-9f3a-88c938c66fe7',
  name: 'why',
  externalId: 'URE202401003C',
  currencyCode: 'EUR',
  balance: 25000.0,
  blockedAmount: 10000.0,
  availableAmount: 15000.0,
  status: 'Active',
  createdAt: dayjs('2025-04-22T13:48:49'),
  updatedAt: dayjs('2025-04-22T18:42:01'),
  companyId: '9fb33462-a549-4efd-b24a-e644267fa380',
  creditLimit: 30000.0,
  usedCredit: 7500.0,
  isMain: true,
  personId: '123e4567-e89b-12d3-a456-426614174000',
  productId: '123e4567-e89b-12d3-a456-426614174001',
  referenceNumber: 'REF001',
  viban: 'DE89370400440532013000',
  cardsCount: 2,
  closeReason: null,
  closedAt: null,
  closedBy: null,
  // Limits
  dailyContactlessPurchase: 1000.0,
  dailyInternetPurchase: 2000.0,
  dailyPurchase: 3000.0,
  dailyWithdrawal: 1000.0,
  monthlyContactlessPurchase: 5000.0,
  monthlyInternetPurchase: 10000.0,
  monthlyPurchase: 15000.0,
  monthlyWithdrawal: 5000.0,
  weeklyContactlessPurchase: 2500.0,
  weeklyInternetPurchase: 5000.0,
  weeklyPurchase: 7500.0,
  weeklyWithdrawal: 2500.0,
  // Top-up details
  bankAddress: 'Bank Street 1',
  bankName: 'Test Bank',
  iban: 'DE89370400440532013000',
  paymentDetails: 'Payment details',
  receiverName: 'Test Receiver',
  registrationNumber: 'REG001',
  swiftCode: 'DEUTDEBBXXX',
};

export const sampleWithNewData: NewCardAccount = {
  name: 'ah',
  externalId: 'URE202401004D',
  currencyCode: 'EUR',
  balance: 18000.0,
  blockedAmount: 6000.0,
  availableAmount: 12000.0,
  status: 'Blocked',
  createdAt: dayjs('2025-04-23T00:08:36'),
  updatedAt: dayjs('2025-04-23T07:08:10'),
  companyId: 'd0b38cad-128e-4989-9997-0345e5ae8991',
  creditLimit: 20000.0,
  usedCredit: 4000.0,
  isMain: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
