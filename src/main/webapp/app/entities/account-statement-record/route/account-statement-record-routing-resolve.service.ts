import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccountStatementRecord } from '../account-statement-record.model';
import { AccountStatementRecordService } from '../service/account-statement-record.service';

const accountStatementRecordResolve = (route: ActivatedRouteSnapshot): Observable<null | IAccountStatementRecord> => {
  const id = route.params.id;
  if (id) {
    return inject(AccountStatementRecordService)
      .find(id)
      .pipe(
        mergeMap((accountStatementRecord: HttpResponse<IAccountStatementRecord>) => {
          if (accountStatementRecord.body) {
            return of(accountStatementRecord.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default accountStatementRecordResolve;
