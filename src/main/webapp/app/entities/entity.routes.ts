import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'company',
    data: { pageTitle: 'Companies' },
    loadChildren: () => import('./company/company.routes'),
  },
  {
    path: 'card-account',
    data: { pageTitle: 'CardAccounts' },
    loadChildren: () => import('./card-account/card-account.routes'),
  },
  {
    path: 'account-statement-record',
    data: { pageTitle: 'AccountStatementRecords' },
    loadChildren: () => import('./account-statement-record/account-statement-record.routes'),
  },
  {
    path: 'card',
    data: { pageTitle: 'Cards' },
    loadChildren: () => import('./card/card.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
