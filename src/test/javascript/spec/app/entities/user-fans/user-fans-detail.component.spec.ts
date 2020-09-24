import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserFansDetailComponent } from 'app/entities/user-fans/user-fans-detail.component';
import { UserFans } from 'app/shared/model/user-fans.model';

describe('Component Tests', () => {
  describe('UserFans Management Detail Component', () => {
    let comp: UserFansDetailComponent;
    let fixture: ComponentFixture<UserFansDetailComponent>;
    const route = ({ data: of({ userFans: new UserFans(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserFansDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserFansDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFansDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userFans on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userFans).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
