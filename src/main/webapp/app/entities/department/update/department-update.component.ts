import { Component, OnInit } from '@angular/core';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IDepartment } from '../department.model';
import { DepartmentFormGroup, DepartmentFormService } from './department-form.service';
import { DepartmentService } from '../service/department.service';
import { UserManagementService } from '../../../admin/user-management/service/user-management.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize, map } from 'rxjs/operators';
import { isPresent } from '../../../core/util/operators';
import { IUser } from '../../../admin/user-management/user-management.model';

@Component({
  standalone: true,
  selector: 'jhi-department-update',
  templateUrl: './department-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartmentUpdateComponent implements OnInit {
  isSaving = false;
  department: IDepartment | null = null;

  userInternalsSharedCollection: IUser[] = [];
  departmentAdminsCollection: IUser[] = [];

  editForm: DepartmentFormGroup = this.departmentFormService.createDepartmentFormGroup();

  constructor(
    protected departmentService: DepartmentService,
    protected departmentFormService: DepartmentFormService,
    protected userInternalService: UserManagementService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ department }) => {
      this.department = department;
      if (department) {
        this.updateForm(department);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const department = this.departmentFormService.getDepartment(this.editForm);
    if (department.id !== null) {
      this.subscribeToSaveResponse(this.departmentService.update(department));
    } else {
      this.subscribeToSaveResponse(this.departmentService.create(department));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(department: IDepartment): void {
    this.department = department;
    this.departmentFormService.resetForm(this.editForm, department);

    this.userInternalsSharedCollection = this.addUserInternalToCollectionIfMissing<IUser>(
      this.userInternalsSharedCollection,
      ...(department.members ?? []),
    );
    this.departmentAdminsCollection = this.addUserInternalToCollectionIfMissing<IUser>(
      this.departmentAdminsCollection,
      department.departmentAdmin,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userInternalService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUser[]) =>
          this.addUserInternalToCollectionIfMissing<IUser>(userInternals, ...(this.department?.members ?? [])),
        ),
      )
      .subscribe((userInternals: IUser[]) => (this.userInternalsSharedCollection = userInternals));

    this.userInternalService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUser[]) => this.addUserInternalToCollectionIfMissing<IUser>(userInternals, this.department?.departmentAdmin)),
      )
      .subscribe((userInternals: IUser[]) => (this.departmentAdminsCollection = userInternals));
  }

  addUserInternalToCollectionIfMissing<Type extends Pick<IUser, 'id'>>(
    userCollection: Type[],
    ...usersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const users: Type[] = usersToCheck.filter(isPresent);
    if (users.length > 0) {
      const userCollectionIdentifiers = userCollection.map(departmentItem => this.getUserIdentifier(departmentItem)!);
      const usersToAdd = users.filter(departmentItem => {
        const userIdentifier = this.getUserIdentifier(departmentItem);
        if (userCollectionIdentifiers.includes(userIdentifier)) {
          return false;
        }
        userCollectionIdentifiers.push(userIdentifier);
        return true;
      });
      return [...usersToAdd, ...userCollection];
    }
    return userCollection;
  }

  private getUserIdentifier(user: IUser): string {
    return user.id!;
  }
}
