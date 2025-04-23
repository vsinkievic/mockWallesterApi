import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICardAccount } from '../card-account.model';
import { CardAccountService } from '../service/card-account.service';

@Component({
  templateUrl: './card-account-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CardAccountDeleteDialogComponent {
  cardAccount?: ICardAccount;

  protected cardAccountService = inject(CardAccountService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.cardAccountService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
