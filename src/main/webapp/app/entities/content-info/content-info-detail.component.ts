import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentInfo } from 'app/shared/model/content-info.model';

@Component({
  selector: 'jhi-content-info-detail',
  templateUrl: './content-info-detail.component.html',
})
export class ContentInfoDetailComponent implements OnInit {
  contentInfo: IContentInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentInfo }) => (this.contentInfo = contentInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
