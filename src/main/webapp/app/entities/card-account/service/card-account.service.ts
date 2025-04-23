import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICardAccount, NewCardAccount } from '../card-account.model';

export type PartialUpdateCardAccount = Partial<ICardAccount> & Pick<ICardAccount, 'id'>;

type RestOf<T extends ICardAccount | NewCardAccount> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestCardAccount = RestOf<ICardAccount>;

export type NewRestCardAccount = RestOf<NewCardAccount>;

export type PartialUpdateRestCardAccount = RestOf<PartialUpdateCardAccount>;

export type EntityResponseType = HttpResponse<ICardAccount>;
export type EntityArrayResponseType = HttpResponse<ICardAccount[]>;

@Injectable({ providedIn: 'root' })
export class CardAccountService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/card-accounts');

  create(cardAccount: NewCardAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardAccount);
    return this.http
      .post<RestCardAccount>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cardAccount: ICardAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardAccount);
    return this.http
      .put<RestCardAccount>(`${this.resourceUrl}/${this.getCardAccountIdentifier(cardAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cardAccount: PartialUpdateCardAccount): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cardAccount);
    return this.http
      .patch<RestCardAccount>(`${this.resourceUrl}/${this.getCardAccountIdentifier(cardAccount)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCardAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCardAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCardAccountIdentifier(cardAccount: Pick<ICardAccount, 'id'>): string {
    return cardAccount.id;
  }

  compareCardAccount(o1: Pick<ICardAccount, 'id'> | null, o2: Pick<ICardAccount, 'id'> | null): boolean {
    return o1 && o2 ? this.getCardAccountIdentifier(o1) === this.getCardAccountIdentifier(o2) : o1 === o2;
  }

  addCardAccountToCollectionIfMissing<Type extends Pick<ICardAccount, 'id'>>(
    cardAccountCollection: Type[],
    ...cardAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cardAccounts: Type[] = cardAccountsToCheck.filter(isPresent);
    if (cardAccounts.length > 0) {
      const cardAccountCollectionIdentifiers = cardAccountCollection.map(cardAccountItem => this.getCardAccountIdentifier(cardAccountItem));
      const cardAccountsToAdd = cardAccounts.filter(cardAccountItem => {
        const cardAccountIdentifier = this.getCardAccountIdentifier(cardAccountItem);
        if (cardAccountCollectionIdentifiers.includes(cardAccountIdentifier)) {
          return false;
        }
        cardAccountCollectionIdentifiers.push(cardAccountIdentifier);
        return true;
      });
      return [...cardAccountsToAdd, ...cardAccountCollection];
    }
    return cardAccountCollection;
  }

  protected convertDateFromClient<T extends ICardAccount | NewCardAccount | PartialUpdateCardAccount>(cardAccount: T): RestOf<T> {
    return {
      ...cardAccount,
      createdAt: cardAccount.createdAt?.toJSON() ?? null,
      updatedAt: cardAccount.updatedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCardAccount: RestCardAccount): ICardAccount {
    return {
      ...restCardAccount,
      createdAt: restCardAccount.createdAt ? dayjs(restCardAccount.createdAt) : undefined,
      updatedAt: restCardAccount.updatedAt ? dayjs(restCardAccount.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCardAccount>): HttpResponse<ICardAccount> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCardAccount[]>): HttpResponse<ICardAccount[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
