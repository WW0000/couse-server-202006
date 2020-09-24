import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Course1802753139TestModule } from '../../../test.module';
import { ContentInfoDetailComponent } from 'app/entities/content-info/content-info-detail.component';
import { ContentInfo } from 'app/shared/model/content-info.model';

describe('Component Tests', () => {
  describe('ContentInfo Management Detail Component', () => {
    let comp: ContentInfoDetailComponent;
    let fixture: ComponentFixture<ContentInfoDetailComponent>;
    const route = ({ data: of({ contentInfo: new ContentInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [ContentInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContentInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contentInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
