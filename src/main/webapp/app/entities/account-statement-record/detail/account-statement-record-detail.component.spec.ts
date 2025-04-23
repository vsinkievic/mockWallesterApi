import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AccountStatementRecordDetailComponent } from './account-statement-record-detail.component';

describe('AccountStatementRecord Management Detail Component', () => {
  let comp: AccountStatementRecordDetailComponent;
  let fixture: ComponentFixture<AccountStatementRecordDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountStatementRecordDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./account-statement-record-detail.component').then(m => m.AccountStatementRecordDetailComponent),
              resolve: { accountStatementRecord: () => of({ id: '7e5354de-30f1-4790-923b-e13be8402cdc' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AccountStatementRecordDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountStatementRecordDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load accountStatementRecord on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AccountStatementRecordDetailComponent);

      // THEN
      expect(instance.accountStatementRecord()).toEqual(expect.objectContaining({ id: '7e5354de-30f1-4790-923b-e13be8402cdc' }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
