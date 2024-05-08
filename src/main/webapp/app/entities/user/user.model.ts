import { INotifySettings } from '../notify-settings/notify-settings.model';

export interface IUser {
  id: string;
  login?: string;
  firstName?: string;
  lastName?: string;
  notifySettings?: INotifySettings[];
}

export class User implements IUser {
  constructor(
    public id: string,
    public login: string,
    public firstName: string,
    public lastName: string,
    public notifySettings: INotifySettings[],
  ) {}
}

export function getUserIdentifier(user: IUser): string {
  return user.id;
}
