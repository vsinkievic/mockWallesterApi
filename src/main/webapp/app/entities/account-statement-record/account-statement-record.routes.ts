import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AccountStatementRecordResolve from './route/account-statement-record-routing-resolve.service';

const accountStatementRecordRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/account-statement-record.component').then(m => m.AccountStatementRecordComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/account-statement-record-detail.component').then(m => m.AccountStatementRecordDetailComponent),
    resolve: {
      accountStatementRecord: AccountStatementRecordResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/account-statement-record-update.component').then(m => m.AccountStatementRecordUpdateComponent),
    resolve: {
      accountStatementRecord: AccountStatementRecordResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/account-statement-record-update.component').then(m => m.AccountStatementRecordUpdateComponent),
    resolve: {
      accountStatementRecord: AccountStatementRecordResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default accountStatementRecordRoute;
