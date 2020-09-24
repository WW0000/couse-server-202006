import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserShare } from 'app/shared/model/user-share.model';
import { UserShareService } from './user-share.service';

@Component({
  templateUrl: './user-share-delete-dialog.component.html',
})
export class UserShareDeleteDialogComponent {
  userShare?: IUserShare;

  constructor(protected userShareService: UserShareService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userShareService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userShareListModification');
      this.activeModal.close();
    });
  }
}
