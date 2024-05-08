export interface IUser {
  id: string | null;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  email?: string;
}

export class User implements IUser {
  constructor(
    public id: string | null,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public email?: string,
  ) {}
}
