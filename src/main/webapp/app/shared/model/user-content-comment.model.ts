import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { IContentInfo } from 'app/shared/model/content-info.model';

export interface IUserContentComment {
  id?: number;
  commentPid?: number;
  commentTime?: Moment;
  commentContent?: string;
  clientType?: string;
  praiseCount?: number;
  account?: IUserAccount;
  content?: IContentInfo;
}

export class UserContentComment implements IUserContentComment {
  constructor(
    public id?: number,
    public commentPid?: number,
    public commentTime?: Moment,
    public commentContent?: string,
    public clientType?: string,
    public praiseCount?: number,
    public account?: IUserAccount,
    public content?: IContentInfo
  ) {}
}
