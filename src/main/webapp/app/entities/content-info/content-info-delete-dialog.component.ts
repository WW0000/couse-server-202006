import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from './content-info.service';

@Component({
  templateUrl: './content-info-delete-dialog.component.html',
})
export class ContentInfoDeleteDialogComponent {
  contentInfo?: IContentInfo;

  constructor(
    protected contentInfoService: ContentInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contentInfoListModification');
      this.activeModal.close();
    });
  }
}
