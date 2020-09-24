import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentInfo } from 'app/shared/model/content-info.model';

type EntityResponseType = HttpResponse<IContentInfo>;
type EntityArrayResponseType = HttpResponse<IContentInfo[]>;

@Injectable({ providedIn: 'root' })
export class ContentInfoService {
  public resourceUrl = SERVER_API_URL + 'api/content-infos';

  constructor(protected http: HttpClient) {}

  create(contentInfo: IContentInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contentInfo);
    return this.http
      .post<IContentInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contentInfo: IContentInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contentInfo);
    return this.http
      .put<IContentInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContentInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContentInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contentInfo: IContentInfo): IContentInfo {
    const copy: IContentInfo = Object.assign({}, contentInfo, {
      contentTime: contentInfo.contentTime && contentInfo.contentTime.isValid() ? contentInfo.contentTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.contentTime = res.body.contentTime ? moment(res.body.contentTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contentInfo: IContentInfo) => {
        contentInfo.contentTime = contentInfo.contentTime ? moment(contentInfo.contentTime) : undefined;
      });
    }
    return res;
  }
}
