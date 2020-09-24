import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserContentPraiseDetailComponent } from 'app/entities/user-content-praise/user-content-praise-detail.component';
import { UserContentPraise } from 'app/shared/model/user-content-praise.model';

describe('Component Tests', () => {
  describe('UserContentPraise Management Detail Component', () => {
    let comp: UserContentPraiseDetailComponent;
    let fixture: ComponentFixture<UserContentPraiseDetailComponent>;
    const route = ({ data: of({ userContentPraise: new UserContentPraise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserContentPraiseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserContentPraiseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserContentPraiseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userContentPraise on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userContentPraise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
