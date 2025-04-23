import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CardAccountDetailComponent } from './card-account-detail.component';

describe('CardAccount Management Detail Component', () => {
  let comp: CardAccountDetailComponent;
  let fixture: ComponentFixture<CardAccountDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardAccountDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./card-account-detail.component').then(m => m.CardAccountDetailComponent),
              resolve: { cardAccount: () => of({ id: '33b21439-f53d-4fa3-8aaa-a84819340555' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CardAccountDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardAccountDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load cardAccount on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CardAccountDetailComponent);

      // THEN
      expect(instance.cardAccount()).toEqual(expect.objectContaining({ id: '33b21439-f53d-4fa3-8aaa-a84819340555' }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
