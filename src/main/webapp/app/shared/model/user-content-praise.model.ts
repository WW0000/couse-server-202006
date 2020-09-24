import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { IContentInfo } from 'app/shared/model/content-info.model';

export interface IUserContentPraise {
  id?: number;
  praiseTime?: Moment;
  account?: IUserAccount;
  content?: IContentInfo;
}

export class UserContentPraise implements IUserContentPraise {
  constructor(public id?: number, public praiseTime?: Moment, public account?: IUserAccount, public content?: IContentInfo) {}
}
