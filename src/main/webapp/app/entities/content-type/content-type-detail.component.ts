import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentType } from 'app/shared/model/content-type.model';

@Component({
  selector: 'jhi-content-type-detail',
  templateUrl: './content-type-detail.component.html',
})
export class ContentTypeDetailComponent implements OnInit {
  contentType: IContentType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentType }) => (this.contentType = contentType));
  }

  previousState(): void {
    window.history.back();
  }
}
