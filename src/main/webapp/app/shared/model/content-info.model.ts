import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { IContentType } from 'app/shared/model/content-type.model';

export interface IContentInfo {
  id?: number;
  contentActor?: string;
  contentInfo?: string;
  contentImg?: string;
  contentTime?: Moment;
  contentPraiseCount?: number;
  contentFavorateCount?: number;
  contentCommentCount?: number;
  contentImgLabel?: string;
  account?: IUserAccount;
  contentType?: IContentType;
}

export class ContentInfo implements IContentInfo {
  constructor(
    public id?: number,
    public contentActor?: string,
    public contentInfo?: string,
    public contentImg?: string,
    public contentTime?: Moment,
    public contentPraiseCount?: number,
    public contentFavorateCount?: number,
    public contentCommentCount?: number,
    public contentImgLabel?: string,
    public account?: IUserAccount,
    public contentType?: IContentType
  ) {}
}
