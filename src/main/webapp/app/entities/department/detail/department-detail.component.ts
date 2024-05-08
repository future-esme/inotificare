import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDepartment } from '../department.model';
import { RouterAccess } from '../router-access';

@Component({
  standalone: true,
  selector: 'jhi-department-detail',
  templateUrl: './department-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DepartmentDetailComponent extends RouterAccess {
  @Input() department: IDepartment | null = null;

  constructor(
    protected activatedRoute: ActivatedRoute,
    public router: Router,
  ) {
    super(router);
  }

  previousState(): void {
    window.history.back();
  }
}
