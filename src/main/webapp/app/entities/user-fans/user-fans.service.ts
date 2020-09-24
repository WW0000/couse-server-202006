import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserFans } from 'app/shared/model/user-fans.model';

type EntityResponseType = HttpResponse<IUserFans>;
type EntityArrayResponseType = HttpResponse<IUserFans[]>;

@Injectable({ providedIn: 'root' })
export class UserFansService {
  public resourceUrl = SERVER_API_URL + 'api/user-fans';

  constructor(protected http: HttpClient) {}

  create(userFans: IUserFans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFans);
    return this.http
      .post<IUserFans>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userFans: IUserFans): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFans);
    return this.http
      .put<IUserFans>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserFans>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserFans[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userFans: IUserFans): IUserFans {
    const copy: IUserFans = Object.assign({}, userFans, {
      fansTime: userFans.fansTime && userFans.fansTime.isValid() ? userFans.fansTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fansTime = res.body.fansTime ? moment(res.body.fansTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userFans: IUserFans) => {
        userFans.fansTime = userFans.fansTime ? moment(userFans.fansTime) : undefined;
      });
    }
    return res;
  }
}
