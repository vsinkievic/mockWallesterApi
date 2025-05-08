import { Component, NgZone, OnInit, inject, signal } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router, RouterModule } from '@angular/router';
import { Observable, Subscription, combineLatest, filter, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SortByDirective, SortDirective, SortService, type SortState, sortStateSignal } from 'app/shared/sort';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { ItemCountComponent } from 'app/shared/pagination';
import { FormsModule } from '@angular/forms';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { DEFAULT_SORT_DATA, ITEM_DELETED_EVENT, SORT } from 'app/config/navigation.constants';
import { IAccountStatementRecord } from '../account-statement-record.model';
import { AccountStatementRecordService, EntityArrayResponseType } from '../service/account-statement-record.service';
import { AccountStatementRecordDeleteDialogComponent } from '../delete/account-statement-record-delete-dialog.component';
import { CardAccountService } from 'app/entities/card-account/service/card-account.service';
import { ICardAccount } from 'app/entities/card-account/card-account.model';

@Component({
  selector: 'jhi-account-statement-record',
  templateUrl: './account-statement-record.component.html',
  imports: [RouterModule, FormsModule, SharedModule, SortDirective, SortByDirective, FormatMediumDatetimePipe, ItemCountComponent],
})
export class AccountStatementRecordComponent implements OnInit {
  subscription: Subscription | null = null;
  accountStatementRecords = signal<IAccountStatementRecord[]>([]);
  accountDetails = signal<ICardAccount | null>(null);
  isLoading = false;
  accountIdFilter = '';

  sortState = sortStateSignal({});

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  public readonly router = inject(Router);
  protected readonly accountStatementRecordService = inject(AccountStatementRecordService);
  protected readonly cardAccountService = inject(CardAccountService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly sortService = inject(SortService);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IAccountStatementRecord): string => this.accountStatementRecordService.getAccountStatementRecordIdentifier(item);

  ngOnInit(): void {
    this.subscription = combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data])
      .pipe(
        tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
        tap(() => this.load()),
      )
      .subscribe();
  }

  delete(accountStatementRecord: IAccountStatementRecord): void {
    const modalRef = this.modalService.open(AccountStatementRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.accountStatementRecord = accountStatementRecord;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.queryBackend().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(event: SortState): void {
    this.handleNavigation(this.page, event);
  }

  navigateToPage(page: number): void {
    this.handleNavigation(page, this.sortState());
  }

  onAccountIdFilterChange(): void {
    this.page = 1;
    this.load();
    this.loadAccountDetails();
  }

  clearAccountIdFilter(): void {
    this.accountIdFilter = '';
    this.accountDetails.set(null);
    this.onAccountIdFilterChange();
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    this.sortState.set(this.sortService.parseSortParam(params.get(SORT) ?? data[DEFAULT_SORT_DATA]));

    // Get account ID from URL if present
    const accountId = params.get('accountId');
    if (accountId) {
      this.accountIdFilter = accountId;
      this.loadAccountDetails();
    }
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.accountStatementRecords.set(dataFromBody);
  }

  protected fillComponentAttributesFromResponseBody(data: IAccountStatementRecord[] | null): IAccountStatementRecord[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(): Observable<EntityArrayResponseType> {
    const { page } = this;

    this.isLoading = true;
    const pageToLoad: number = page;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(this.sortState()),
      accountId: this.accountIdFilter,
    };
    return this.accountStatementRecordService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page: number, sortState: SortState): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.sortService.buildSortParam(sortState),
      accountId: this.accountIdFilter,
    };

    this.ngZone.run(() => {
      this.router.navigate(['./'], {
        relativeTo: this.activatedRoute,
        queryParams: queryParamsObj,
      });
    });
  }

  private loadAccountDetails(): void {
    if (this.accountIdFilter) {
      this.cardAccountService.find(this.accountIdFilter).subscribe({
        next: (response: { body: ICardAccount | null }) => {
          this.accountDetails.set(response.body);
        },
        error: () => {
          this.accountDetails.set(null);
        },
      });
    } else {
      this.accountDetails.set(null);
    }
  }
}
