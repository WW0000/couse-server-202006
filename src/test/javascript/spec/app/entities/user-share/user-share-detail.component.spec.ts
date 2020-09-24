import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserShareDetailComponent } from 'app/entities/user-share/user-share-detail.component';
import { UserShare } from 'app/shared/model/user-share.model';

describe('Component Tests', () => {
  describe('UserShare Management Detail Component', () => {
    let comp: UserShareDetailComponent;
    let fixture: ComponentFixture<UserShareDetailComponent>;
    const route = ({ data: of({ userShare: new UserShare(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserShareDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserShareDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserShareDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userShare on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userShare).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
