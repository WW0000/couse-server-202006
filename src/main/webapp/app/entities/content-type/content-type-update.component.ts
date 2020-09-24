import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContentType, ContentType } from 'app/shared/model/content-type.model';
import { ContentTypeService } from './content-type.service';

@Component({
  selector: 'jhi-content-type-update',
  templateUrl: './content-type-update.component.html',
})
export class ContentTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contentTypeName: [],
    contentTypeSort: [],
    contentTypeTime: [],
    contentTypeUpdateCount: [],
  });

  constructor(protected contentTypeService: ContentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentType }) => {
      if (!contentType.id) {
        const today = moment().startOf('day');
        contentType.contentTypeTime = today;
      }

      this.updateForm(contentType);
    });
  }

  updateForm(contentType: IContentType): void {
    this.editForm.patchValue({
      id: contentType.id,
      contentTypeName: contentType.contentTypeName,
      contentTypeSort: contentType.contentTypeSort,
      contentTypeTime: contentType.contentTypeTime ? contentType.contentTypeTime.format(DATE_TIME_FORMAT) : null,
      contentTypeUpdateCount: contentType.contentTypeUpdateCount,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentType = this.createFromForm();
    if (contentType.id !== undefined) {
      this.subscribeToSaveResponse(this.contentTypeService.update(contentType));
    } else {
      this.subscribeToSaveResponse(this.contentTypeService.create(contentType));
    }
  }

  private createFromForm(): IContentType {
    return {
      ...new ContentType(),
      id: this.editForm.get(['id'])!.value,
      contentTypeName: this.editForm.get(['contentTypeName'])!.value,
      contentTypeSort: this.editForm.get(['contentTypeSort'])!.value,
      contentTypeTime: this.editForm.get(['contentTypeTime'])!.value
        ? moment(this.editForm.get(['contentTypeTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      contentTypeUpdateCount: this.editForm.get(['contentTypeUpdateCount'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentType>>): void {
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
}
