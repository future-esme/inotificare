import { IUser } from '../../admin/user-management/user-management.model';

export interface IDepartment {
  id: string;
  title?: string | null;
  departmentAdmin?: IUser | null;
  members?: IUser[] | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };

export enum DepartmentRouter {
  ADMIN = 'departments',
  USER = 'my-departments',
}
