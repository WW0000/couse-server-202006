import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { IContentInfo } from 'app/shared/model/content-info.model';

export interface IUserShare {
  id?: number;
  shareTime?: Moment;
  account?: IUserAccount;
  content?: IContentInfo;
}

export class UserShare implements IUserShare {
  constructor(public id?: number, public shareTime?: Moment, public account?: IUserAccount, public content?: IContentInfo) {}
}
