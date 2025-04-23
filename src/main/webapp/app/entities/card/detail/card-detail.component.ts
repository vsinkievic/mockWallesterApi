import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { ICard } from '../card.model';

@Component({
  selector: 'jhi-card-detail',
  templateUrl: './card-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class CardDetailComponent {
  card = input<ICard | null>(null);

  previousState(): void {
    window.history.back();
  }
}
