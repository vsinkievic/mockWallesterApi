import dayjs from 'dayjs/esm';

import { ICardAccount, NewCardAccount } from './card-account.model';

export const sampleWithRequiredData: ICardAccount = {
  id: '5a9347f5-d90a-4408-8cc5-b5d9808d587e',
  accountNumber: 'frankly besides',
  currency: 'UYU',
  balance: 32445.81,
  reservedAmount: 14728.93,
  availableAmount: 31081.35,
  blockedAmount: 3242.2,
  status: 'Closed',
  createdAt: dayjs('2025-04-22T15:09'),
  updatedAt: dayjs('2025-04-22T16:04'),
};

export const sampleWithPartialData: ICardAccount = {
  id: 'e463c913-8815-4d38-b730-8a9bf16c0100',
  accountNumber: 'ugh',
  currency: 'AFN',
  balance: 9234.86,
  reservedAmount: 2397.88,
  availableAmount: 6880.09,
  blockedAmount: 24933.62,
  status: 'Closed',
  createdAt: dayjs('2025-04-23T06:14'),
  updatedAt: dayjs('2025-04-22T17:34'),
};

export const sampleWithFullData: ICardAccount = {
  id: 'a9e9eb2f-f233-4c33-9f3a-88c938c66fe7',
  accountNumber: 'underneath or despite',
  currency: 'UYI',
  balance: 27532.21,
  reservedAmount: 25595.16,
  availableAmount: 7183.09,
  blockedAmount: 31282.24,
  status: 'Active',
  createdAt: dayjs('2025-04-23T04:10'),
  updatedAt: dayjs('2025-04-23T08:12'),
};

export const sampleWithNewData: NewCardAccount = {
  accountNumber: 'pomelo',
  currency: 'MKD',
  balance: 4584.59,
  reservedAmount: 4946.11,
  availableAmount: 30363.02,
  blockedAmount: 6828.78,
  status: 'Active',
  createdAt: dayjs('2025-04-23T04:51'),
  updatedAt: dayjs('2025-04-22T19:30'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
