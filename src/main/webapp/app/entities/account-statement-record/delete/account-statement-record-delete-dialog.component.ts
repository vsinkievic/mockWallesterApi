import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAccountStatementRecord } from '../account-statement-record.model';
import { AccountStatementRecordService } from '../service/account-statement-record.service';

@Component({
  templateUrl: './account-statement-record-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AccountStatementRecordDeleteDialogComponent {
  accountStatementRecord?: IAccountStatementRecord;

  protected accountStatementRecordService = inject(AccountStatementRecordService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.accountStatementRecordService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
