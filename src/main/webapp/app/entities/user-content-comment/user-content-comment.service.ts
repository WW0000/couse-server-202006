import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserContentComment } from 'app/shared/model/user-content-comment.model';

type EntityResponseType = HttpResponse<IUserContentComment>;
type EntityArrayResponseType = HttpResponse<IUserContentComment[]>;

@Injectable({ providedIn: 'root' })
export class UserContentCommentService {
  public resourceUrl = SERVER_API_URL + 'api/user-content-comments';

  constructor(protected http: HttpClient) {}

  create(userContentComment: IUserContentComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userContentComment);
    return this.http
      .post<IUserContentComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userContentComment: IUserContentComment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userContentComment);
    return this.http
      .put<IUserContentComment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserContentComment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserContentComment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userContentComment: IUserContentComment): IUserContentComment {
    const copy: IUserContentComment = Object.assign({}, userContentComment, {
      commentTime:
        userContentComment.commentTime && userContentComment.commentTime.isValid() ? userContentComment.commentTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.commentTime = res.body.commentTime ? moment(res.body.commentTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userContentComment: IUserContentComment) => {
        userContentComment.commentTime = userContentComment.commentTime ? moment(userContentComment.commentTime) : undefined;
      });
    }
    return res;
  }
}
