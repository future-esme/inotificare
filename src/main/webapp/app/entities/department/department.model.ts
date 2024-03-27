import { IUser } from '../user/user.model';

export interface IDepartment {
  id: string;
  title?: string | null;
  departmentAdmin?: IUser | null;
  members?: IUser[] | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };
