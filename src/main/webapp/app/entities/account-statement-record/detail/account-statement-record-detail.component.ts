import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { IAccountStatementRecord } from '../account-statement-record.model';

@Component({
  selector: 'jhi-account-statement-record-detail',
  templateUrl: './account-statement-record-detail.component.html',
  imports: [SharedModule, RouterModule, FormatMediumDatetimePipe],
})
export class AccountStatementRecordDetailComponent {
  accountStatementRecord = input<IAccountStatementRecord | null>(null);

  previousState(): void {
    window.history.back();
  }
}
