import dayjs from 'dayjs/esm';
import { CurrencyCode } from 'app/entities/enumerations/currency-code.model';
import { AccountStatus } from 'app/entities/enumerations/account-status.model';

export interface ICardAccount {
  id: string;
  externalId?: string | null;
  name?: string | null;
  currencyCode?: keyof typeof CurrencyCode | null;
  balance?: number | null;
  blockedAmount?: number | null;
  availableAmount?: number | null;
  status?: keyof typeof AccountStatus | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;

  // Additional fields
  cardsCount?: number | null;
  closeReason?: string | null;
  closedAt?: dayjs.Dayjs | null;
  closedBy?: string | null;
  companyId?: string | null;
  creditLimit?: number | null;
  isMain?: boolean | null;
  personId?: string | null;
  productId?: string | null;
  referenceNumber?: string | null;
  usedCredit?: number | null;
  viban?: string | null;

  // Limits
  dailyContactlessPurchase?: number | null;
  dailyInternetPurchase?: number | null;
  dailyPurchase?: number | null;
  dailyWithdrawal?: number | null;
  monthlyContactlessPurchase?: number | null;
  monthlyInternetPurchase?: number | null;
  monthlyPurchase?: number | null;
  monthlyWithdrawal?: number | null;
  weeklyContactlessPurchase?: number | null;
  weeklyInternetPurchase?: number | null;
  weeklyPurchase?: number | null;
  weeklyWithdrawal?: number | null;

  // Top-up details
  bankAddress?: string | null;
  bankName?: string | null;
  iban?: string | null;
  paymentDetails?: string | null;
  receiverName?: string | null;
  registrationNumber?: string | null;
  swiftCode?: string | null;
}

export type NewCardAccount = Omit<ICardAccount, 'id'> & { id: null };
