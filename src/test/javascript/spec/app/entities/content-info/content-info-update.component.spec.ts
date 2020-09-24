import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { ContentInfoUpdateComponent } from 'app/entities/content-info/content-info-update.component';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';
import { ContentInfo } from 'app/shared/model/content-info.model';

describe('Component Tests', () => {
  describe('ContentInfo Management Update Component', () => {
    let comp: ContentInfoUpdateComponent;
    let fixture: ComponentFixture<ContentInfoUpdateComponent>;
    let service: ContentInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [ContentInfoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContentInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContentInfo(123);
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
        const entity = new ContentInfo();
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
