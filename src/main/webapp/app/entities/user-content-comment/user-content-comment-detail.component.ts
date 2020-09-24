import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserContentComment } from 'app/shared/model/user-content-comment.model';

@Component({
  selector: 'jhi-user-content-comment-detail',
  templateUrl: './user-content-comment-detail.component.html',
})
export class UserContentCommentDetailComponent implements OnInit {
  userContentComment: IUserContentComment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userContentComment }) => (this.userContentComment = userContentComment));
  }

  previousState(): void {
    window.history.back();
  }
}
