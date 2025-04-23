import dayjs from 'dayjs/esm';
import { CountryCode } from 'app/entities/enumerations/country-code.model';
import { RiskProfile } from 'app/entities/enumerations/risk-profile.model';
import { KybStatus } from 'app/entities/enumerations/kyb-status.model';
import { AccountStatus } from 'app/entities/enumerations/account-status.model';
import { LanguageCode } from 'app/entities/enumerations/language-code.model';

export interface ICompany {
  id: string;
  name?: string | null;
  registrationNumber?: string | null;
  regAddressCountryCode?: keyof typeof CountryCode | null;
  regAddress1?: string | null;
  regAddress2?: string | null;
  regAddressCity?: string | null;
  regAddressPostalCode?: string | null;
  hqAddressCountryCode?: keyof typeof CountryCode | null;
  hqAddress1?: string | null;
  hqAddress2?: string | null;
  hqAddressCity?: string | null;
  hqAddressPostalCode?: string | null;
  riskProfile?: keyof typeof RiskProfile | null;
  mobile?: string | null;
  email?: string | null;
  createdAt?: dayjs.Dayjs | null;
  createdBy?: string | null;
  updatedAt?: dayjs.Dayjs | null;
  updatedBy?: string | null;
  deletedAt?: dayjs.Dayjs | null;
  deletedBy?: string | null;
  industryType?: string | null;
  dateOfIncorporation?: dayjs.Dayjs | null;
  businessRelationshipPurpose?: string | null;
  isSanctionsRelated?: boolean | null;
  isAdverseMediaInvolved?: boolean | null;
  employeesQuantity?: string | null;
  cardSpendingAmount?: string | null;
  limitDailyPurchase?: number | null;
  limitDailyWithdrawal?: number | null;
  limitMonthlyPurchase?: number | null;
  limitMonthlyWithdrawal?: number | null;
  kybStatus?: keyof typeof KybStatus | null;
  status?: keyof typeof AccountStatus | null;
  pushNotificationsEnabled?: boolean | null;
  preferredLanguageCode?: keyof typeof LanguageCode | null;
  vatNumber?: string | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
