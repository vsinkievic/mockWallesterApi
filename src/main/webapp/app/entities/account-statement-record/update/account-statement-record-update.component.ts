import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AccountStatementRecordType } from 'app/entities/enumerations/account-statement-record-type.model';
import { AccountStatementRecordGroup } from 'app/entities/enumerations/account-statement-record-group.model';
import { CurrencyCode } from 'app/entities/enumerations/currency-code.model';
import { MerchantCategoryCode } from 'app/entities/enumerations/merchant-category-code.model';
import { CountryCode } from 'app/entities/enumerations/country-code.model';
import { AccountStatementRecordStatus } from 'app/entities/enumerations/account-statement-record-status.model';
import { AccountStatementRecordResponse } from 'app/entities/enumerations/account-statement-record-response.model';
import { AccountStatementRecordService } from '../service/account-statement-record.service';
import { IAccountStatementRecord } from '../account-statement-record.model';
import { AccountStatementRecordFormGroup, AccountStatementRecordFormService } from './account-statement-record-form.service';
import { CardService } from 'app/entities/card/service/card.service';
import { CardAccountService } from 'app/entities/card-account/service/card-account.service';

@Component({
  selector: 'jhi-account-statement-record-update',
  templateUrl: './account-statement-record-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AccountStatementRecordUpdateComponent implements OnInit {
  isSaving = false;
  accountStatementRecord: IAccountStatementRecord | null = null;
  accountStatementRecordTypeValues = Object.keys(AccountStatementRecordType);
  accountStatementRecordGroupValues = Object.keys(AccountStatementRecordGroup);
  currencyCodeValues = Object.keys(CurrencyCode);
  merchantCategoryCodeValues = Object.values(MerchantCategoryCode).map(value => value.replace('MCC', ''));
  countryCodeValues = Object.keys(CountryCode);
  accountStatementRecordStatusValues = Object.keys(AccountStatementRecordStatus);
  accountStatementRecordResponseValues = Object.keys(AccountStatementRecordResponse);

  protected accountStatementRecordService = inject(AccountStatementRecordService);
  protected accountStatementRecordFormService = inject(AccountStatementRecordFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected cardService = inject(CardService);
  protected cardAccountService = inject(CardAccountService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AccountStatementRecordFormGroup = this.accountStatementRecordFormService.createAccountStatementRecordFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountStatementRecord }) => {
      this.accountStatementRecord = accountStatementRecord;
      if (accountStatementRecord) {
        this.updateForm(accountStatementRecord);
      } else {
        // Handle query parameters for new record
        this.activatedRoute.queryParams.subscribe(params => {
          const accountId = params['accountId'];
          const cardId = params['cardId'];

          if (accountId) {
            this.cardAccountService.find(accountId).subscribe(response => {
              if (response.body) {
                this.editForm.patchValue({
                  accountId: response.body.id,
                  accountCurrencyCode: response.body.currencyCode,
                });
              }
            });
          }

          if (cardId) {
            this.cardService.find(cardId).subscribe(response => {
              if (response.body) {
                this.editForm.patchValue({
                  cardId: response.body.id,
                  accountId: response.body.accountId,
                });
              }
            });
          }
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountStatementRecord = this.accountStatementRecordFormService.getAccountStatementRecord(this.editForm);
    if (accountStatementRecord.id !== null) {
      this.subscribeToSaveResponse(this.accountStatementRecordService.update(accountStatementRecord));
    } else {
      this.subscribeToSaveResponse(this.accountStatementRecordService.create(accountStatementRecord));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountStatementRecord>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(accountStatementRecord: IAccountStatementRecord): void {
    this.accountStatementRecord = accountStatementRecord;
    this.accountStatementRecordFormService.resetForm(this.editForm, accountStatementRecord);
  }
}
