import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import CardAccountResolve from './route/card-account-routing-resolve.service';

const cardAccountRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/card-account.component').then(m => m.CardAccountComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/card-account-detail.component').then(m => m.CardAccountDetailComponent),
    resolve: {
      cardAccount: CardAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/card-account-update.component').then(m => m.CardAccountUpdateComponent),
    resolve: {
      cardAccount: CardAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/card-account-update.component').then(m => m.CardAccountUpdateComponent),
    resolve: {
      cardAccount: CardAccountResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cardAccountRoute;
