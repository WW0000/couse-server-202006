import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserFans, UserFans } from 'app/shared/model/user-fans.model';
import { UserFansService } from './user-fans.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account/user-account.service';

@Component({
  selector: 'jhi-user-fans-update',
  templateUrl: './user-fans-update.component.html',
})
export class UserFansUpdateComponent implements OnInit {
  isSaving = false;
  useraccounts: IUserAccount[] = [];

  editForm = this.fb.group({
    id: [],
    fansTime: [],
    fansFrom: [],
    fansTo: [],
  });

  constructor(
    protected userFansService: UserFansService,
    protected userAccountService: UserAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userFans }) => {
      if (!userFans.id) {
        const today = moment().startOf('day');
        userFans.fansTime = today;
      }

      this.updateForm(userFans);

      this.userAccountService.query().subscribe((res: HttpResponse<IUserAccount[]>) => (this.useraccounts = res.body || []));
    });
  }

  updateForm(userFans: IUserFans): void {
    this.editForm.patchValue({
      id: userFans.id,
      fansTime: userFans.fansTime ? userFans.fansTime.format(DATE_TIME_FORMAT) : null,
      fansFrom: userFans.fansFrom,
      fansTo: userFans.fansTo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userFans = this.createFromForm();
    if (userFans.id !== undefined) {
      this.subscribeToSaveResponse(this.userFansService.update(userFans));
    } else {
      this.subscribeToSaveResponse(this.userFansService.create(userFans));
    }
  }

  private createFromForm(): IUserFans {
    return {
      ...new UserFans(),
      id: this.editForm.get(['id'])!.value,
      fansTime: this.editForm.get(['fansTime'])!.value ? moment(this.editForm.get(['fansTime'])!.value, DATE_TIME_FORMAT) : undefined,
      fansFrom: this.editForm.get(['fansFrom'])!.value,
      fansTo: this.editForm.get(['fansTo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserFans>>): void {
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

  trackById(index: number, item: IUserAccount): any {
    return item.id;
  }
}
