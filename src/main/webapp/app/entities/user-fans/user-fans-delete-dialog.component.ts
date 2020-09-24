import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserFans } from 'app/shared/model/user-fans.model';
import { UserFansService } from './user-fans.service';

@Component({
  templateUrl: './user-fans-delete-dialog.component.html',
})
export class UserFansDeleteDialogComponent {
  userFans?: IUserFans;

  constructor(protected userFansService: UserFansService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userFansService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userFansListModification');
      this.activeModal.close();
    });
  }
}
