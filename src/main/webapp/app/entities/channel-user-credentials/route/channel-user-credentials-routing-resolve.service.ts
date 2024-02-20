import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IChannelUserCredentials } from '../channel-user-credentials.model';
import { ChannelUserCredentialsService } from '../service/channel-user-credentials.service';

export const channelUserCredentialsResolve = (route: ActivatedRouteSnapshot): Observable<null | IChannelUserCredentials> => {
  const id = route.params['id'];
  if (id) {
    return inject(ChannelUserCredentialsService)
      .find(id)
      .pipe(
        mergeMap((channelUserCredentials: HttpResponse<IChannelUserCredentials>) => {
          if (channelUserCredentials.body) {
            return of(channelUserCredentials.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default channelUserCredentialsResolve;
