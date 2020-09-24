import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Course1802753139TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { UserFavorateItemDeleteDialogComponent } from 'app/entities/user-favorate-item/user-favorate-item-delete-dialog.component';
import { UserFavorateItemService } from 'app/entities/user-favorate-item/user-favorate-item.service';

describe('Component Tests', () => {
  describe('UserFavorateItem Management Delete Component', () => {
    let comp: UserFavorateItemDeleteDialogComponent;
    let fixture: ComponentFixture<UserFavorateItemDeleteDialogComponent>;
    let service: UserFavorateItemService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Course1802753139TestModule],
        declarations: [UserFavorateItemDeleteDialogComponent],
      })
        .overrideTemplate(UserFavorateItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserFavorateItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserFavorateItemService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
