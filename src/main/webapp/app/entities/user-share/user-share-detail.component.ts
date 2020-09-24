import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserShare } from 'app/shared/model/user-share.model';

@Component({
  selector: 'jhi-user-share-detail',
  templateUrl: './user-share-detail.component.html',
})
export class UserShareDetailComponent implements OnInit {
  userShare: IUserShare | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userShare }) => (this.userShare = userShare));
  }

  previousState(): void {
    window.history.back();
  }
}
