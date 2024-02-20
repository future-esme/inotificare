import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ChannelUserCredentialsComponent } from './list/channel-user-credentials.component';
import { ChannelUserCredentialsDetailComponent } from './detail/channel-user-credentials-detail.component';
import { ChannelUserCredentialsUpdateComponent } from './update/channel-user-credentials-update.component';
import ChannelUserCredentialsResolve from './route/channel-user-credentials-routing-resolve.service';

const channelUserCredentialsRoute: Routes = [
  {
    path: '',
    component: ChannelUserCredentialsComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ChannelUserCredentialsDetailComponent,
    resolve: {
      channelUserCredentials: ChannelUserCredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ChannelUserCredentialsUpdateComponent,
    resolve: {
      channelUserCredentials: ChannelUserCredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ChannelUserCredentialsUpdateComponent,
    resolve: {
      channelUserCredentials: ChannelUserCredentialsResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default channelUserCredentialsRoute;
