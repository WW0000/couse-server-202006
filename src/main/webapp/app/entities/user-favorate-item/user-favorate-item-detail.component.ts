import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserFavorateItem } from 'app/shared/model/user-favorate-item.model';

@Component({
  selector: 'jhi-user-favorate-item-detail',
  templateUrl: './user-favorate-item-detail.component.html',
})
export class UserFavorateItemDetailComponent implements OnInit {
  userFavorateItem: IUserFavorateItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFavorateItem }) => (this.userFavorateItem = userFavorateItem));
  }

  previousState(): void {
    window.history.back();
  }
}
