import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccountStatementRecord, NewAccountStatementRecord } from '../account-statement-record.model';

export type PartialUpdateAccountStatementRecord = Partial<IAccountStatementRecord> & Pick<IAccountStatementRecord, 'id'>;

type RestOf<T extends IAccountStatementRecord | NewAccountStatementRecord> = Omit<T, 'date' | 'markedForDisputeAt' | 'purchaseDate'> & {
  date?: string | null;
  markedForDisputeAt?: string | null;
  purchaseDate?: string | null;
};

export type RestAccountStatementRecord = RestOf<IAccountStatementRecord>;

export type NewRestAccountStatementRecord = RestOf<NewAccountStatementRecord>;

export type PartialUpdateRestAccountStatementRecord = RestOf<PartialUpdateAccountStatementRecord>;

export type EntityResponseType = HttpResponse<IAccountStatementRecord>;
export type EntityArrayResponseType = HttpResponse<IAccountStatementRecord[]>;

@Injectable({ providedIn: 'root' })
export class AccountStatementRecordService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/account-statement-records');

  create(accountStatementRecord: NewAccountStatementRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountStatementRecord);
    return this.http
      .post<RestAccountStatementRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(accountStatementRecord: IAccountStatementRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountStatementRecord);
    return this.http
      .put<RestAccountStatementRecord>(`${this.resourceUrl}/${this.getAccountStatementRecordIdentifier(accountStatementRecord)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(accountStatementRecord: PartialUpdateAccountStatementRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountStatementRecord);
    return this.http
      .patch<RestAccountStatementRecord>(`${this.resourceUrl}/${this.getAccountStatementRecordIdentifier(accountStatementRecord)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestAccountStatementRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAccountStatementRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAccountStatementRecordIdentifier(accountStatementRecord: Pick<IAccountStatementRecord, 'id'>): string {
    return accountStatementRecord.id;
  }

  compareAccountStatementRecord(o1: Pick<IAccountStatementRecord, 'id'> | null, o2: Pick<IAccountStatementRecord, 'id'> | null): boolean {
    return o1 && o2 ? this.getAccountStatementRecordIdentifier(o1) === this.getAccountStatementRecordIdentifier(o2) : o1 === o2;
  }

  addAccountStatementRecordToCollectionIfMissing<Type extends Pick<IAccountStatementRecord, 'id'>>(
    accountStatementRecordCollection: Type[],
    ...accountStatementRecordsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const accountStatementRecords: Type[] = accountStatementRecordsToCheck.filter(isPresent);
    if (accountStatementRecords.length > 0) {
      const accountStatementRecordCollectionIdentifiers = accountStatementRecordCollection.map(accountStatementRecordItem =>
        this.getAccountStatementRecordIdentifier(accountStatementRecordItem),
      );
      const accountStatementRecordsToAdd = accountStatementRecords.filter(accountStatementRecordItem => {
        const accountStatementRecordIdentifier = this.getAccountStatementRecordIdentifier(accountStatementRecordItem);
        if (accountStatementRecordCollectionIdentifiers.includes(accountStatementRecordIdentifier)) {
          return false;
        }
        accountStatementRecordCollectionIdentifiers.push(accountStatementRecordIdentifier);
        return true;
      });
      return [...accountStatementRecordsToAdd, ...accountStatementRecordCollection];
    }
    return accountStatementRecordCollection;
  }

  protected convertDateFromClient<T extends IAccountStatementRecord | NewAccountStatementRecord | PartialUpdateAccountStatementRecord>(
    accountStatementRecord: T,
  ): RestOf<T> {
    return {
      ...accountStatementRecord,
      date: accountStatementRecord.date?.toJSON() ?? null,
      markedForDisputeAt: accountStatementRecord.markedForDisputeAt?.toJSON() ?? null,
      purchaseDate: accountStatementRecord.purchaseDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAccountStatementRecord: RestAccountStatementRecord): IAccountStatementRecord {
    return {
      ...restAccountStatementRecord,
      date: restAccountStatementRecord.date ? dayjs(restAccountStatementRecord.date) : undefined,
      markedForDisputeAt: restAccountStatementRecord.markedForDisputeAt ? dayjs(restAccountStatementRecord.markedForDisputeAt) : undefined,
      purchaseDate: restAccountStatementRecord.purchaseDate ? dayjs(restAccountStatementRecord.purchaseDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAccountStatementRecord>): HttpResponse<IAccountStatementRecord> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAccountStatementRecord[]>): HttpResponse<IAccountStatementRecord[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
