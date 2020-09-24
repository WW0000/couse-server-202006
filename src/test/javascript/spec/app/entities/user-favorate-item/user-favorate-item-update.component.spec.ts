import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { UserFavorateItemUpdateComponent } from 'app/entities/user-favorate-item/user-favorate-item-update.component';
import { UserFavorateItemService } from 'app/entities/user-favorate-item/user-favorate-item.service';
import { UserFavorateItem } from 'app/shared/model/user-favorate-item.model';

describe('Component Tests', () => {
  describe('UserFavorateItem Management Update Component', () => {
    let comp: UserFavorateItemUpdateComponent;
    let fixture: ComponentFixture<UserFavorateItemUpdateComponent>;
    let service: UserFavorateItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserFavorateItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserFavorateItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserFavorateItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFavorateItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserFavorateItem(123);
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
        const entity = new UserFavorateItem();
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
