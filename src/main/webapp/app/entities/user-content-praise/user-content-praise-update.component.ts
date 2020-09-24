import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserContentPraise, UserContentPraise } from 'app/shared/model/user-content-praise.model';
import { UserContentPraiseService } from './user-content-praise.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';

type SelectableEntity = IUserAccount | IContentInfo;

@Component({
  selector: 'jhi-user-content-praise-update',
  templateUrl: './user-content-praise-update.component.html',
})
export class UserContentPraiseUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];
  contentinfos: IContentInfo[] = [];

  editForm = this.fb.group({
    id: [],
    praiseTime: [],
    account: [],
    content: [],
  });

  constructor(
    protected userContentPraiseService: UserContentPraiseService,
    protected userAccountService: UserAccountService,
    protected contentInfoService: ContentInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userContentPraise }) => {
      if (!userContentPraise.id) {
        const today = moment().startOf('day');
        userContentPraise.praiseTime = today;
      }

      this.updateForm(userContentPraise);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));

      this.contentInfoService.query().subscribe((res: HttpResponse<IContentInfo[]>) => (this.contentinfos = res.body || []));
    });
  }

  updateForm(userContentPraise: IUserContentPraise): void {
    this.editForm.patchValue({
      id: userContentPraise.id,
      praiseTime: userContentPraise.praiseTime ? userContentPraise.praiseTime.format(DATE_TIME_FORMAT) : null,
      account: userContentPraise.account,
      content: userContentPraise.content,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userContentPraise = this.createFromForm();
    if (userContentPraise.id !== undefined) {
      this.subscribeToSaveResponse(this.userContentPraiseService.update(userContentPraise));
    } else {
      this.subscribeToSaveResponse(this.userContentPraiseService.create(userContentPraise));
    }
  }

  private createFromForm(): IUserContentPraise {
    return {
      ...new UserContentPraise(),
      id: this.editForm.get(['id'])!.value,
      praiseTime: this.editForm.get(['praiseTime'])!.value ? moment(this.editForm.get(['praiseTime'])!.value, DATE_TIME_FORMAT) : undefined,
      account: this.editForm.get(['account'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserContentPraise>>): void {
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
