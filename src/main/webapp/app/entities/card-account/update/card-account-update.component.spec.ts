import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { CardAccountService } from '../service/card-account.service';
import { ICardAccount } from '../card-account.model';
import { CardAccountFormService } from './card-account-form.service';

import { CardAccountUpdateComponent } from './card-account-update.component';

describe('CardAccount Management Update Component', () => {
  let comp: CardAccountUpdateComponent;
  let fixture: ComponentFixture<CardAccountUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cardAccountFormService: CardAccountFormService;
  let cardAccountService: CardAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CardAccountUpdateComponent],
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
      .overrideTemplate(CardAccountUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CardAccountUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cardAccountFormService = TestBed.inject(CardAccountFormService);
    cardAccountService = TestBed.inject(CardAccountService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const cardAccount: ICardAccount = { id: 'fbb864af-c72c-45d6-8833-269c5dcf5136' };

      activatedRoute.data = of({ cardAccount });
      comp.ngOnInit();

      expect(comp.cardAccount).toEqual(cardAccount);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardAccount>>();
      const cardAccount = { id: '33b21439-f53d-4fa3-8aaa-a84819340555' };
      jest.spyOn(cardAccountFormService, 'getCardAccount').mockReturnValue(cardAccount);
      jest.spyOn(cardAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardAccount }));
      saveSubject.complete();

      // THEN
      expect(cardAccountFormService.getCardAccount).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cardAccountService.update).toHaveBeenCalledWith(expect.objectContaining(cardAccount));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardAccount>>();
      const cardAccount = { id: '33b21439-f53d-4fa3-8aaa-a84819340555' };
      jest.spyOn(cardAccountFormService, 'getCardAccount').mockReturnValue({ id: null });
      jest.spyOn(cardAccountService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardAccount: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cardAccount }));
      saveSubject.complete();

      // THEN
      expect(cardAccountFormService.getCardAccount).toHaveBeenCalled();
      expect(cardAccountService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICardAccount>>();
      const cardAccount = { id: '33b21439-f53d-4fa3-8aaa-a84819340555' };
      jest.spyOn(cardAccountService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cardAccount });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cardAccountService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
