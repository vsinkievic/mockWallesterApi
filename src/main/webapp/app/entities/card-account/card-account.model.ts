import dayjs from 'dayjs/esm';
import { CurrencyCode } from 'app/entities/enumerations/currency-code.model';
import { AccountStatus } from 'app/entities/enumerations/account-status.model';

export interface ICardAccount {
  id: string;
  accountNumber?: string | null;
  currency?: keyof typeof CurrencyCode | null;
  balance?: number | null;
  reservedAmount?: number | null;
  availableAmount?: number | null;
  blockedAmount?: number | null;
  status?: keyof typeof AccountStatus | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
}

export type NewCardAccount = Omit<ICardAccount, 'id'> & { id: null };
