import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserContentCommentUpdateComponent } from 'app/entities/user-content-comment/user-content-comment-update.component';
import { UserContentCommentService } from 'app/entities/user-content-comment/user-content-comment.service';
import { UserContentComment } from 'app/shared/model/user-content-comment.model';

describe('Component Tests', () => {
  describe('UserContentComment Management Update Component', () => {
    let comp: UserContentCommentUpdateComponent;
    let fixture: ComponentFixture<UserContentCommentUpdateComponent>;
    let service: UserContentCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserContentCommentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserContentCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserContentCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserContentCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserContentComment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserContentComment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
