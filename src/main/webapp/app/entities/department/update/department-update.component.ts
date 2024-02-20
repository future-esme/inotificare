import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUserInternal } from 'app/entities/user-internal/user-internal.model';
import { UserInternalService } from 'app/entities/user-internal/service/user-internal.service';
import { IDepartment } from '../department.model';
import { DepartmentService } from '../service/department.service';
import { DepartmentFormService, DepartmentFormGroup } from './department-form.service';

@Component({
  standalone: true,
  selector: 'jhi-department-update',
  templateUrl: './department-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DepartmentUpdateComponent implements OnInit {
  isSaving = false;
  department: IDepartment | null = null;

  userInternalsSharedCollection: IUserInternal[] = [];
  departmentAdminsCollection: IUserInternal[] = [];

  editForm: DepartmentFormGroup = this.departmentFormService.createDepartmentFormGroup();

  constructor(
    protected departmentService: DepartmentService,
    protected departmentFormService: DepartmentFormService,
    protected userInternalService: UserInternalService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUserInternal = (o1: IUserInternal | null, o2: IUserInternal | null): boolean =>
    this.userInternalService.compareUserInternal(o1, o2);

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

    this.userInternalsSharedCollection = this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(
      this.userInternalsSharedCollection,
      ...(department.members ?? []),
    );
    this.departmentAdminsCollection = this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(
      this.departmentAdminsCollection,
      department.departmentAdmin,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userInternalService
      .query()
      .pipe(map((res: HttpResponse<IUserInternal[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUserInternal[]) =>
          this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(userInternals, ...(this.department?.members ?? [])),
        ),
      )
      .subscribe((userInternals: IUserInternal[]) => (this.userInternalsSharedCollection = userInternals));

    this.userInternalService
      .query({ 'departmentId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUserInternal[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUserInternal[]) =>
          this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(userInternals, this.department?.departmentAdmin),
        ),
      )
      .subscribe((userInternals: IUserInternal[]) => (this.departmentAdminsCollection = userInternals));
  }
}
