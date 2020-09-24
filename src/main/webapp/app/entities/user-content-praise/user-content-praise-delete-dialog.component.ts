import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserContentPraise } from 'app/shared/model/user-content-praise.model';
import { UserContentPraiseService } from './user-content-praise.service';

@Component({
  templateUrl: './user-content-praise-delete-dialog.component.html',
})
export class UserContentPraiseDeleteDialogComponent {
  userContentPraise?: IUserContentPraise;

  constructor(
    protected userContentPraiseService: UserContentPraiseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userContentPraiseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userContentPraiseListModification');
      this.activeModal.close();
    });
  }
}
