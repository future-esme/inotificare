import { IUserInternal } from 'app/entities/user-internal/user-internal.model';

export interface IDepartment {
  id: string;
  title?: string | null;
  departmentAdmin?: IUserInternal | null;
  members?: IUserInternal[] | null;
}

export type NewDepartment = Omit<IDepartment, 'id'> & { id: null };
