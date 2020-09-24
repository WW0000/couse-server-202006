import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserFavorateItem } from 'app/shared/model/user-favorate-item.model';

type EntityResponseType = HttpResponse<IUserFavorateItem>;
type EntityArrayResponseType = HttpResponse<IUserFavorateItem[]>;

@Injectable({ providedIn: 'root' })
export class UserFavorateItemService {
  public resourceUrl = SERVER_API_URL + 'api/user-favorate-items';

  constructor(protected http: HttpClient) {}

  create(userFavorateItem: IUserFavorateItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFavorateItem);
    return this.http
      .post<IUserFavorateItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userFavorateItem: IUserFavorateItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userFavorateItem);
    return this.http
      .put<IUserFavorateItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserFavorateItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserFavorateItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userFavorateItem: IUserFavorateItem): IUserFavorateItem {
    const copy: IUserFavorateItem = Object.assign({}, userFavorateItem, {
      favorateTime:
        userFavorateItem.favorateTime && userFavorateItem.favorateTime.isValid() ? userFavorateItem.favorateTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.favorateTime = res.body.favorateTime ? moment(res.body.favorateTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userFavorateItem: IUserFavorateItem) => {
        userFavorateItem.favorateTime = userFavorateItem.favorateTime ? moment(userFavorateItem.favorateTime) : undefined;
      });
    }
    return res;
  }
}
