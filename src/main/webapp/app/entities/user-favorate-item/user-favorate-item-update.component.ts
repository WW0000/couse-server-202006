import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserFavorateItem, UserFavorateItem } from 'app/shared/model/user-favorate-item.model';
import { UserFavorateItemService } from './user-favorate-item.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from 'app/entities/content-info/content-info.service';

type SelectableEntity = IUserAccount | IContentInfo;

@Component({
  selector: 'jhi-user-favorate-item-update',
  templateUrl: './user-favorate-item-update.component.html',
})
export class UserFavorateItemUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];
  contentinfos: IContentInfo[] = [];

  editForm = this.fb.group({
    id: [],
    favorateTime: [],
    account: [],
    content: [],
  });

  constructor(
    protected userFavorateItemService: UserFavorateItemService,
    protected userAccountService: UserAccountService,
    protected contentInfoService: ContentInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFavorateItem }) => {
      if (!userFavorateItem.id) {
        const today = moment().startOf('day');
        userFavorateItem.favorateTime = today;
      }

      this.updateForm(userFavorateItem);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));

      this.contentInfoService.query().subscribe((res: HttpResponse<IContentInfo[]>) => (this.contentinfos = res.body || []));
    });
  }

  updateForm(userFavorateItem: IUserFavorateItem): void {
    this.editForm.patchValue({
      id: userFavorateItem.id,
      favorateTime: userFavorateItem.favorateTime ? userFavorateItem.favorateTime.format(DATE_TIME_FORMAT) : null,
      account: userFavorateItem.account,
      content: userFavorateItem.content,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userFavorateItem = this.createFromForm();
    if (userFavorateItem.id !== undefined) {
      this.subscribeToSaveResponse(this.userFavorateItemService.update(userFavorateItem));
    } else {
      this.subscribeToSaveResponse(this.userFavorateItemService.create(userFavorateItem));
    }
  }

  private createFromForm(): IUserFavorateItem {
    return {
      ...new UserFavorateItem(),
      id: this.editForm.get(['id'])!.value,
      favorateTime: this.editForm.get(['favorateTime'])!.value
        ? moment(this.editForm.get(['favorateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      account: this.editForm.get(['account'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserFavorateItem>>): void {
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
