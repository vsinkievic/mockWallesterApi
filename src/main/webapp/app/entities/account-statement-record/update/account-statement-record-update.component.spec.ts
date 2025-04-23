import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AccountStatementRecordService } from '../service/account-statement-record.service';
import { IAccountStatementRecord } from '../account-statement-record.model';
import { AccountStatementRecordFormService } from './account-statement-record-form.service';

import { AccountStatementRecordUpdateComponent } from './account-statement-record-update.component';

describe('AccountStatementRecord Management Update Component', () => {
  let comp: AccountStatementRecordUpdateComponent;
  let fixture: ComponentFixture<AccountStatementRecordUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accountStatementRecordFormService: AccountStatementRecordFormService;
  let accountStatementRecordService: AccountStatementRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AccountStatementRecordUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AccountStatementRecordUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountStatementRecordUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accountStatementRecordFormService = TestBed.inject(AccountStatementRecordFormService);
    accountStatementRecordService = TestBed.inject(AccountStatementRecordService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const accountStatementRecord: IAccountStatementRecord = { id: '1c489a8c-dd9b-4903-a4eb-3fa38058e239' };

      activatedRoute.data = of({ accountStatementRecord });
      comp.ngOnInit();

      expect(comp.accountStatementRecord).toEqual(accountStatementRecord);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountStatementRecord>>();
      const accountStatementRecord = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
      jest.spyOn(accountStatementRecordFormService, 'getAccountStatementRecord').mockReturnValue(accountStatementRecord);
      jest.spyOn(accountStatementRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountStatementRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountStatementRecord }));
      saveSubject.complete();

      // THEN
      expect(accountStatementRecordFormService.getAccountStatementRecord).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accountStatementRecordService.update).toHaveBeenCalledWith(expect.objectContaining(accountStatementRecord));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountStatementRecord>>();
      const accountStatementRecord = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
      jest.spyOn(accountStatementRecordFormService, 'getAccountStatementRecord').mockReturnValue({ id: null });
      jest.spyOn(accountStatementRecordService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountStatementRecord: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountStatementRecord }));
      saveSubject.complete();

      // THEN
      expect(accountStatementRecordFormService.getAccountStatementRecord).toHaveBeenCalled();
      expect(accountStatementRecordService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountStatementRecord>>();
      const accountStatementRecord = { id: '7e5354de-30f1-4790-923b-e13be8402cdc' };
      jest.spyOn(accountStatementRecordService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountStatementRecord });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accountStatementRecordService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
