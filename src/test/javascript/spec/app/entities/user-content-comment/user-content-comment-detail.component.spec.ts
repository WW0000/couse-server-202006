import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserContentCommentDetailComponent } from 'app/entities/user-content-comment/user-content-comment-detail.component';
import { UserContentComment } from 'app/shared/model/user-content-comment.model';

describe('Component Tests', () => {
  describe('UserContentComment Management Detail Component', () => {
    let comp: UserContentCommentDetailComponent;
    let fixture: ComponentFixture<UserContentCommentDetailComponent>;
    const route = ({ data: of({ userContentComment: new UserContentComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserContentCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserContentCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserContentCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userContentComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userContentComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
