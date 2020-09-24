import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserContentComment } from 'app/shared/model/user-content-comment.model';
import { UserContentCommentService } from './user-content-comment.service';

@Component({
  templateUrl: './user-content-comment-delete-dialog.component.html',
})
export class UserContentCommentDeleteDialogComponent {
  userContentComment?: IUserContentComment;

  constructor(
    protected userContentCommentService: UserContentCommentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userContentCommentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userContentCommentListModification');
      this.activeModal.close();
    });
  }
}
