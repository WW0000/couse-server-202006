import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserFansUpdateComponent } from 'app/entities/user-fans/user-fans-update.component';
import { UserFansService } from 'app/entities/user-fans/user-fans.service';
import { UserFans } from 'app/shared/model/user-fans.model';

describe('Component Tests', () => {
  describe('UserFans Management Update Component', () => {
    let comp: UserFansUpdateComponent;
    let fixture: ComponentFixture<UserFansUpdateComponent>;
    let service: UserFansService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserFansUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserFansUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFansUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFansService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFans(123);
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
        const entity = new UserFans();
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
