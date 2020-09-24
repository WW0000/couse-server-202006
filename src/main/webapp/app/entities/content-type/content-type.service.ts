import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentType } from 'app/shared/model/content-type.model';

type EntityResponseType = HttpResponse<IContentType>;
type EntityArrayResponseType = HttpResponse<IContentType[]>;

@Injectable({ providedIn: 'root' })
export class ContentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/content-types';

  constructor(protected http: HttpClient) {}

  create(contentType: IContentType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contentType);
    return this.http
      .post<IContentType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contentType: IContentType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contentType);
    return this.http
      .put<IContentType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContentType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContentType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contentType: IContentType): IContentType {
    const copy: IContentType = Object.assign({}, contentType, {
      contentTypeTime:
        contentType.contentTypeTime && contentType.contentTypeTime.isValid() ? contentType.contentTypeTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.contentTypeTime = res.body.contentTypeTime ? moment(res.body.contentTypeTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contentType: IContentType) => {
        contentType.contentTypeTime = contentType.contentTypeTime ? moment(contentType.contentTypeTime) : undefined;
      });
    }
    return res;
  }
}
