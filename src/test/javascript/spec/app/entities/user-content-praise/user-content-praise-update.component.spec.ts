import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserContentPraiseUpdateComponent } from 'app/entities/user-content-praise/user-content-praise-update.component';
import { UserContentPraiseService } from 'app/entities/user-content-praise/user-content-praise.service';
import { UserContentPraise } from 'app/shared/model/user-content-praise.model';

describe('Component Tests', () => {
  describe('UserContentPraise Management Update Component', () => {
    let comp: UserContentPraiseUpdateComponent;
    let fixture: ComponentFixture<UserContentPraiseUpdateComponent>;
    let service: UserContentPraiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserContentPraiseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserContentPraiseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserContentPraiseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserContentPraiseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserContentPraise(123);
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
        const entity = new UserContentPraise();
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
