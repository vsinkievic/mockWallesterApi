import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICardAccount } from '../card-account.model';
import { CardAccountService } from '../service/card-account.service';

const cardAccountResolve = (route: ActivatedRouteSnapshot): Observable<null | ICardAccount> => {
  const id = route.params.id;
  if (id) {
    return inject(CardAccountService)
      .find(id)
      .pipe(
        mergeMap((cardAccount: HttpResponse<ICardAccount>) => {
          if (cardAccount.body) {
            return of(cardAccount.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default cardAccountResolve;
