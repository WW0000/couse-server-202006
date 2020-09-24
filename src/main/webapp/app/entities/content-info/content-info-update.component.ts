import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContentInfo, ContentInfo } from 'app/shared/model/content-info.model';
import { ContentInfoService } from './content-info.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';
import { IContentType } from 'app/shared/model/content-type.model';
import { ContentTypeService } from 'app/entities/content-type/content-type.service';

type SelectableEntity = IUserAccount | IContentType;

@Component({
  selector: 'jhi-content-info-update',
  templateUrl: './content-info-update.component.html',
})
export class ContentInfoUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];
  contenttypes: IContentType[] = [];

  editForm = this.fb.group({
    id: [],
    contentActor: [],
    contentInfo: [],
    contentImg: [],
    contentTime: [],
    contentPraiseCount: [],
    contentFavorateCount: [],
    contentCommentCount: [],
    contentImgLabel: [],
    account: [],
    contentType: [],
  });

  constructor(
    protected contentInfoService: ContentInfoService,
    protected userAccountService: UserAccountService,
    protected contentTypeService: ContentTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentInfo }) => {
      if (!contentInfo.id) {
        const today = moment().startOf('day');
        contentInfo.contentTime = today;
      }

      this.updateForm(contentInfo);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));

      this.contentTypeService.query().subscribe((res: HttpResponse<IContentType[]>) => (this.contenttypes = res.body || []));
    });
  }

  updateForm(contentInfo: IContentInfo): void {
    this.editForm.patchValue({
      id: contentInfo.id,
      contentActor: contentInfo.contentActor,
      contentInfo: contentInfo.contentInfo,
      contentImg: contentInfo.contentImg,
      contentTime: contentInfo.contentTime ? contentInfo.contentTime.format(DATE_TIME_FORMAT) : null,
      contentPraiseCount: contentInfo.contentPraiseCount,
      contentFavorateCount: contentInfo.contentFavorateCount,
      contentCommentCount: contentInfo.contentCommentCount,
      contentImgLabel: contentInfo.contentImgLabel,
      account: contentInfo.account,
      contentType: contentInfo.contentType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentInfo = this.createFromForm();
    if (contentInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.contentInfoService.update(contentInfo));
    } else {
      this.subscribeToSaveResponse(this.contentInfoService.create(contentInfo));
    }
  }

  private createFromForm(): IContentInfo {
    return {
      ...new ContentInfo(),
      id: this.editForm.get(['id'])!.value,
      contentActor: this.editForm.get(['contentActor'])!.value,
      contentInfo: this.editForm.get(['contentInfo'])!.value,
      contentImg: this.editForm.get(['contentImg'])!.value,
      contentTime: this.editForm.get(['contentTime'])!.value
        ? moment(this.editForm.get(['contentTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      contentPraiseCount: this.editForm.get(['contentPraiseCount'])!.value,
      contentFavorateCount: this.editForm.get(['contentFavorateCount'])!.value,
      contentCommentCount: this.editForm.get(['contentCommentCount'])!.value,
      contentImgLabel: this.editForm.get(['contentImgLabel'])!.value,
      account: this.editForm.get(['account'])!.value,
      contentType: this.editForm.get(['contentType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentInfo>>): void {
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
