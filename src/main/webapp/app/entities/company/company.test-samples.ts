import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: '35d5b00e-b671-45d6-9494-de893569aea0',
};

export const sampleWithPartialData: ICompany = {
  id: '757d323e-2f8d-4e65-844a-b6c2c1923e08',
  name: 'toward',
  regAddressCountryCode: 'TKM',
  regAddress2: 'reflate silver',
  regAddressPostalCode: 'rapid brr yippee',
  hqAddressCountryCode: 'MUS',
  hqAddress2: 'ouch',
  hqAddressCity: 'that',
  email: 'Elliott_Langworth85@hotmail.com',
  createdAt: dayjs('2025-04-23T01:08'),
  createdBy: 'self-confidence digit after',
  deletedBy: 'fluffy viciously',
  businessRelationshipPurpose: 'where bah',
  isAdverseMediaInvolved: true,
  employeesQuantity: 'past',
  limitDailyPurchase: 28486.13,
  kybStatus: 'Verified',
  status: 'Active',
  pushNotificationsEnabled: false,
};

export const sampleWithFullData: ICompany = {
  id: '60a7a5aa-131c-4a7c-926b-fa5d49359941',
  name: 'among amidst',
  registrationNumber: 'pleasure reservation',
  regAddressCountryCode: 'LIE',
  regAddress1: 'slake',
  regAddress2: 'kowtow around mysteriously',
  regAddressCity: 'uh-huh patiently following',
  regAddressPostalCode: 'unselfish notarize hmph',
  hqAddressCountryCode: 'TTO',
  hqAddress1: 'import',
  hqAddress2: 'lecture anenst tightly',
  hqAddressCity: 'on within worriedly',
  hqAddressPostalCode: 'forenenst',
  riskProfile: 'Medium',
  mobile: 'weakly so',
  email: 'Eric67@hotmail.com',
  createdAt: dayjs('2025-04-22T23:46'),
  createdBy: 'shadowy cleverly',
  updatedAt: dayjs('2025-04-23T06:04'),
  updatedBy: 'stitcher while',
  deletedAt: dayjs('2025-04-22T18:50'),
  deletedBy: 'underneath frankly juicy',
  industryType: 'devastation',
  dateOfIncorporation: dayjs('2025-04-22T23:49'),
  businessRelationshipPurpose: 'atop er vein',
  isSanctionsRelated: false,
  isAdverseMediaInvolved: true,
  employeesQuantity: 'usually',
  cardSpendingAmount: 'event notwithstanding out',
  limitDailyPurchase: 16981.44,
  limitDailyWithdrawal: 16423.84,
  limitMonthlyPurchase: 4857.37,
  limitMonthlyWithdrawal: 7728.62,
  kybStatus: 'Pending',
  status: 'Closed',
  pushNotificationsEnabled: true,
  preferredLanguageCode: 'ENG',
  vatNumber: 'brr',
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
