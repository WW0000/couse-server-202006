import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';

export interface IUserFans {
  id?: number;
  fansTime?: Moment;
  fansFrom?: IUserAccount;
  fansTo?: IUserAccount;
}

export class UserFans implements IUserFans {
  constructor(public id?: number, public fansTime?: Moment, public fansFrom?: IUserAccount, public fansTo?: IUserAccount) {}
}
