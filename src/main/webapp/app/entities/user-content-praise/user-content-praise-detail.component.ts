import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserContentPraise } from 'app/shared/model/user-content-praise.model';

@Component({
  selector: 'jhi-user-content-praise-detail',
  templateUrl: './user-content-praise-detail.component.html',
})
export class UserContentPraiseDetailComponent implements OnInit {
  userContentPraise: IUserContentPraise | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userContentPraise }) => (this.userContentPraise = userContentPraise));
  }

  previousState(): void {
    window.history.back();
  }
}
