import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { IContentInfo } from 'app/shared/model/content-info.model';

export interface IUserFavorateItem {
  id?: number;
  favorateTime?: Moment;
  account?: IUserAccount;
  content?: IContentInfo;
}

export class UserFavorateItem implements IUserFavorateItem {
  constructor(public id?: number, public favorateTime?: Moment, public account?: IUserAccount, public content?: IContentInfo) {}
}
