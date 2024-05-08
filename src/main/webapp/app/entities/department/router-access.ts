import { Router } from '@angular/router';
import { DepartmentRouter } from './department.model';

export class RouterAccess {
  protected isMyDepartments = true;
  protected viewRouterLink = DepartmentRouter.USER;
  constructor(public router: Router) {
    this.detectRouter();
  }

  protected detectRouter(): void {
    if (!this.router.url.includes(DepartmentRouter.USER)) {
      this.isMyDepartments = false;
      this.viewRouterLink = DepartmentRouter.ADMIN;
    }
  }
}
