import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CardDetailComponent } from './card-detail.component';

describe('Card Management Detail Component', () => {
  let comp: CardDetailComponent;
  let fixture: ComponentFixture<CardDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./card-detail.component').then(m => m.CardDetailComponent),
              resolve: { card: () => of({ id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CardDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CardDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load card on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CardDetailComponent);

      // THEN
      expect(instance.card()).toEqual(expect.objectContaining({ id: 'a0e18168-f45c-48e4-b16a-0a0b8da485bd' }));
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
