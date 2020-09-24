import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserShare, UserShare } from 'app/shared/model/user-share.model';
import { UserShareService } from './user-share.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';

type SelectableEntity = IUserAccount | IContentInfo;

@Component({
  selector: 'jhi-user-share-update',
  templateUrl: './user-share-update.component.html',
})
export class UserShareUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];
  contentinfos: IContentInfo[] = [];

  editForm = this.fb.group({
    id: [],
    shareTime: [],
    account: [],
    content: [],
  });

  constructor(
    protected userShareService: UserShareService,
    protected userAccountService: UserAccountService,
    protected contentInfoService: ContentInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userShare }) => {
      if (!userShare.id) {
        const today = moment().startOf('day');
        userShare.shareTime = today;
      }

      this.updateForm(userShare);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));

      this.contentInfoService.query().subscribe((res: HttpResponse<IContentInfo[]>) => (this.contentinfos = res.body || []));
    });
  }

  updateForm(userShare: IUserShare): void {
    this.editForm.patchValue({
      id: userShare.id,
      shareTime: userShare.shareTime ? userShare.shareTime.format(DATE_TIME_FORMAT) : null,
      account: userShare.account,
      content: userShare.content,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userShare = this.createFromForm();
    if (userShare.id !== undefined) {
      this.subscribeToSaveResponse(this.userShareService.update(userShare));
    } else {
      this.subscribeToSaveResponse(this.userShareService.create(userShare));
    }
  }

  private createFromForm(): IUserShare {
    return {
      ...new UserShare(),
      id: this.editForm.get(['id'])!.value,
      shareTime: this.editForm.get(['shareTime'])!.value ? moment(this.editForm.get(['shareTime'])!.value, DATE_TIME_FORMAT) : undefined,
      account: this.editForm.get(['account'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserShare>>): void {
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
