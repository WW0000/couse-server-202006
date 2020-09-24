import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserContentComment, UserContentComment } from 'app/shared/model/user-content-comment.model';
import { UserContentCommentService } from './user-content-comment.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';

type SelectableEntity = IUserAccount | IContentInfo;

@Component({
  selector: 'jhi-user-content-comment-update',
  templateUrl: './user-content-comment-update.component.html',
})
export class UserContentCommentUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];
  contentinfos: IContentInfo[] = [];

  editForm = this.fb.group({
    id: [],
    commentPid: [],
    commentTime: [],
    commentContent: [],
    clientType: [],
    praiseCount: [],
    account: [],
    content: [],
  });

  constructor(
    protected userContentCommentService: UserContentCommentService,
    protected userAccountService: UserAccountService,
    protected contentInfoService: ContentInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userContentComment }) => {
      if (!userContentComment.id) {
        const today = moment().startOf('day');
        userContentComment.commentTime = today;
      }

      this.updateForm(userContentComment);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));

      this.contentInfoService.query().subscribe((res: HttpResponse<IContentInfo[]>) => (this.contentinfos = res.body || []));
    });
  }

  updateForm(userContentComment: IUserContentComment): void {
    this.editForm.patchValue({
      id: userContentComment.id,
      commentPid: userContentComment.commentPid,
      commentTime: userContentComment.commentTime ? userContentComment.commentTime.format(DATE_TIME_FORMAT) : null,
      commentContent: userContentComment.commentContent,
      clientType: userContentComment.clientType,
      praiseCount: userContentComment.praiseCount,
      account: userContentComment.account,
      content: userContentComment.content,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userContentComment = this.createFromForm();
    if (userContentComment.id !== undefined) {
      this.subscribeToSaveResponse(this.userContentCommentService.update(userContentComment));
    } else {
      this.subscribeToSaveResponse(this.userContentCommentService.create(userContentComment));
    }
  }

  private createFromForm(): IUserContentComment {
    return {
      ...new UserContentComment(),
      id: this.editForm.get(['id'])!.value,
      commentPid: this.editForm.get(['commentPid'])!.value,
      commentTime: this.editForm.get(['commentTime'])!.value
        ? moment(this.editForm.get(['commentTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      commentContent: this.editForm.get(['commentContent'])!.value,
      clientType: this.editForm.get(['clientType'])!.value,
      praiseCount: this.editForm.get(['praiseCount'])!.value,
      account: this.editForm.get(['account'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserContentComment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
