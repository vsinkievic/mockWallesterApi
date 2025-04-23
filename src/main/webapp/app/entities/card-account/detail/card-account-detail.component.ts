import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { ICardAccount } from '../card-account.model';

@Component({
  selector: 'jhi-card-account-detail',
  templateUrl: './card-account-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class CardAccountDetailComponent {
  cardAccount = input<ICardAccount | null>(null);

  previousState(): void {
    window.history.back();
  }
}
