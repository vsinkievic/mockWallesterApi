import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CurrencyCode } from 'app/entities/enumerations/currency-code.model';
import { AccountStatus } from 'app/entities/enumerations/account-status.model';
import { ICardAccount } from '../card-account.model';
import { CardAccountService } from '../service/card-account.service';
import { CardAccountFormGroup, CardAccountFormService } from './card-account-form.service';

@Component({
  selector: 'jhi-card-account-update',
  templateUrl: './card-account-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CardAccountUpdateComponent implements OnInit {
  isSaving = false;
  cardAccount: ICardAccount | null = null;
  currencyCodeValues = Object.keys(CurrencyCode);
  accountStatusValues = Object.keys(AccountStatus);

  protected cardAccountService = inject(CardAccountService);
  protected cardAccountFormService = inject(CardAccountFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CardAccountFormGroup = this.cardAccountFormService.createCardAccountFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cardAccount }) => {
      this.cardAccount = cardAccount;
      if (cardAccount) {
        this.updateForm(cardAccount);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cardAccount = this.cardAccountFormService.getCardAccount(this.editForm);
    if (cardAccount.id !== null) {
      this.subscribeToSaveResponse(this.cardAccountService.update(cardAccount));
    } else {
      this.subscribeToSaveResponse(this.cardAccountService.create(cardAccount));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICardAccount>>): void {
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

  protected updateForm(cardAccount: ICardAccount): void {
    this.cardAccount = cardAccount;
    this.cardAccountFormService.resetForm(this.editForm, cardAccount);
  }
}
