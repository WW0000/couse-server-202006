import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserFavorateItemDetailComponent } from 'app/entities/user-favorate-item/user-favorate-item-detail.component';
import { UserFavorateItem } from 'app/shared/model/user-favorate-item.model';

describe('Component Tests', () => {
  describe('UserFavorateItem Management Detail Component', () => {
    let comp: UserFavorateItemDetailComponent;
    let fixture: ComponentFixture<UserFavorateItemDetailComponent>;
    const route = ({ data: of({ userFavorateItem: new UserFavorateItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserFavorateItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserFavorateItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFavorateItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userFavorateItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userFavorateItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
