import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { ContentTypeDetailComponent } from 'app/entities/content-type/content-type-detail.component';
import { ContentType } from 'app/shared/model/content-type.model';

describe('Component Tests', () => {
  describe('ContentType Management Detail Component', () => {
    let comp: ContentTypeDetailComponent;
    let fixture: ComponentFixture<ContentTypeDetailComponent>;
    const route = ({ data: of({ contentType: new ContentType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [ContentTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContentTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contentType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
