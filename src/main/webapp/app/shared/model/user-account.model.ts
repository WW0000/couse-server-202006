export interface IUserAccount {
  id?: number;
  head?: string;
  nickName?: string;
  birthday?: string;
  login?: string;
  introduction?: string;
}

export class UserAccount implements IUserAccount {
  constructor(
    public id?: number,
    public head?: string,
    public nickName?: string,
    public birthday?: string,
    public login?: string,
    public introduction?: string
  ) {}
}
