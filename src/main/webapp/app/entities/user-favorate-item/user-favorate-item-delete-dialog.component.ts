import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserFavorateItem } from 'app/shared/model/user-favorate-item.model';
import { UserFavorateItemService } from './user-favorate-item.service';

@Component({
  templateUrl: './user-favorate-item-delete-dialog.component.html',
})
export class UserFavorateItemDeleteDialogComponent {
  userFavorateItem?: IUserFavorateItem;

  constructor(
    protected userFavorateItemService: UserFavorateItemService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userFavorateItemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userFavorateItemListModification');
      this.activeModal.close();
    });
  }
}
