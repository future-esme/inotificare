import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestDataComponent } from './list/request-data.component';
import { RequestDataDetailComponent } from './detail/request-data-detail.component';
import { RequestDataUpdateComponent } from './update/request-data-update.component';
import RequestDataResolve from './route/request-data-routing-resolve.service';

const requestDataRoute: Routes = [
  {
    path: '',
    component: RequestDataComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestDataDetailComponent,
    resolve: {
      requestData: RequestDataResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestDataUpdateComponent,
    resolve: {
      requestData: RequestDataResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestDataUpdateComponent,
    resolve: {
      requestData: RequestDataResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestDataRoute;
