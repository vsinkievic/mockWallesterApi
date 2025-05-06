import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CardType } from 'app/entities/enumerations/card-type.model';
import { CardBlockType } from 'app/entities/enumerations/block-type.model';
import { CardStatus } from 'app/entities/enumerations/card-status.model';
import { Secure3DType } from 'app/entities/enumerations/secure-3-d-type.model';
import { LanguageCode } from 'app/entities/enumerations/language-code.model';
import { CountryCode } from 'app/entities/enumerations/country-code.model';
import { DispatchMethod } from 'app/entities/enumerations/dispatch-method.model';
import { CarrierType } from 'app/entities/enumerations/carrier-type.model';
import { CardCloseReason } from 'app/entities/enumerations/card-close-reason.model';
import { DisposableType } from 'app/entities/enumerations/disposable-type.model';
import { CardService } from '../service/card.service';
import { ICard } from '../card.model';
import { CardFormGroup, CardFormService } from './card-form.service';

@Component({
  selector: 'jhi-card-update',
  templateUrl: './card-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CardUpdateComponent implements OnInit {
  isSaving = false;
  card: ICard | null = null;
  cardTypeValues = Object.keys(CardType);
  blockTypeValues = Object.keys(CardBlockType);
  cardStatusValues = Object.keys(CardStatus);
  secure3DTypeValues = Object.keys(Secure3DType);
  languageCodeValues = Object.keys(LanguageCode);
  countryCodeValues = Object.keys(CountryCode);
  dispatchMethodValues = Object.keys(DispatchMethod);
  carrierTypeValues = Object.keys(CarrierType);
  cardCloseReasonValues = Object.keys(CardCloseReason);
  disposableTypeValues = Object.keys(DisposableType);

  protected cardService = inject(CardService);
  protected cardFormService = inject(CardFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CardFormGroup = this.cardFormService.createCardFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ card }) => {
      this.card = card;
      if (card) {
        this.updateForm(card);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const card = this.cardFormService.getCard(this.editForm);
    if (card.id !== null) {
      this.subscribeToSaveResponse(this.cardService.update(card));
    } else {
      this.subscribeToSaveResponse(this.cardService.create(card));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICard>>): void {
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

  protected updateForm(card: ICard): void {
    this.card = card;
    this.cardFormService.resetForm(this.editForm, card);
  }
}
