import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICard, NewCard } from '../card.model';

export type PartialUpdateCard = Partial<ICard> & Pick<ICard, 'id'>;

type RestOf<T extends ICard | NewCard> = Omit<
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

export type RestCard = RestOf<ICard>;

export type NewRestCard = RestOf<NewCard>;

export type PartialUpdateRestCard = RestOf<PartialUpdateCard>;

export type EntityResponseType = HttpResponse<ICard>;
export type EntityArrayResponseType = HttpResponse<ICard[]>;

@Injectable({ providedIn: 'root' })
export class CardService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cards');

  create(card: NewCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(card);
    return this.http.post<RestCard>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(card: ICard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(card);
    return this.http
      .put<RestCard>(`${this.resourceUrl}/${this.getCardIdentifier(card)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(card: PartialUpdateCard): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(card);
    return this.http
      .patch<RestCard>(`${this.resourceUrl}/${this.getCardIdentifier(card)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCard>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCard[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCardIdentifier(card: Pick<ICard, 'id'>): string {
    return card.id;
  }

  compareCard(o1: Pick<ICard, 'id'> | null, o2: Pick<ICard, 'id'> | null): boolean {
    return o1 && o2 ? this.getCardIdentifier(o1) === this.getCardIdentifier(o2) : o1 === o2;
  }

  addCardToCollectionIfMissing<Type extends Pick<ICard, 'id'>>(
    cardCollection: Type[],
    ...cardsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cards: Type[] = cardsToCheck.filter(isPresent);
    if (cards.length > 0) {
      const cardCollectionIdentifiers = cardCollection.map(cardItem => this.getCardIdentifier(cardItem));
      const cardsToAdd = cards.filter(cardItem => {
        const cardIdentifier = this.getCardIdentifier(cardItem);
        if (cardCollectionIdentifiers.includes(cardIdentifier)) {
          return false;
        }
        cardCollectionIdentifiers.push(cardIdentifier);
        return true;
      });
      return [...cardsToAdd, ...cardCollection];
    }
    return cardCollection;
  }

  protected convertDateFromClient<T extends ICard | NewCard | PartialUpdateCard>(card: T): RestOf<T> {
    return {
      ...card,
      expiryDate: card.expiryDate?.toJSON() ?? null,
      blockedAt: card.blockedAt?.toJSON() ?? null,
      activatedAt: card.activatedAt?.toJSON() ?? null,
      createdAt: card.createdAt?.toJSON() ?? null,
      updatedAt: card.updatedAt?.toJSON() ?? null,
      closedAt: card.closedAt?.toJSON() ?? null,
      dispatchedAt: card.dispatchedAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCard: RestCard): ICard {
    return {
      ...restCard,
      expiryDate: restCard.expiryDate ? dayjs(restCard.expiryDate) : undefined,
      blockedAt: restCard.blockedAt ? dayjs(restCard.blockedAt) : undefined,
      activatedAt: restCard.activatedAt ? dayjs(restCard.activatedAt) : undefined,
      createdAt: restCard.createdAt ? dayjs(restCard.createdAt) : undefined,
      updatedAt: restCard.updatedAt ? dayjs(restCard.updatedAt) : undefined,
      closedAt: restCard.closedAt ? dayjs(restCard.closedAt) : undefined,
      dispatchedAt: restCard.dispatchedAt ? dayjs(restCard.dispatchedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCard>): HttpResponse<ICard> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCard[]>): HttpResponse<ICard[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
