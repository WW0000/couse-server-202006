import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserFans } from 'app/shared/model/user-fans.model';

@Component({
  selector: 'jhi-user-fans-detail',
  templateUrl: './user-fans-detail.component.html',
})
export class UserFansDetailComponent implements OnInit {
  userFans: IUserFans | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFans }) => (this.userFans = userFans));
  }

  previousState(): void {
    window.history.back();
  }
}
