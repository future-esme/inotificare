import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestData } from '../request-data.model';
import { RequestDataService } from '../service/request-data.service';

export const requestDataResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestData> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestDataService)
      .find(id)
      .pipe(
        mergeMap((requestData: HttpResponse<IRequestData>) => {
          if (requestData.body) {
            return of(requestData.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestDataResolve;
