import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserShareUpdateComponent } from 'app/entities/user-share/user-share-update.component';
import { UserShareService } from 'app/entities/user-share/user-share.service';
import { UserShare } from 'app/shared/model/user-share.model';

describe('Component Tests', () => {
  describe('UserShare Management Update Component', () => {
    let comp: UserShareUpdateComponent;
    let fixture: ComponentFixture<UserShareUpdateComponent>;
    let service: UserShareService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserShareUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserShareUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserShareUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserShareService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserShare(123);
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
        const entity = new UserShare();
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
