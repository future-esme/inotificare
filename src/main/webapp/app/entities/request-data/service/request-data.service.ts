import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestData, NewRequestData } from '../request-data.model';

export type PartialUpdateRequestData = Partial<IRequestData> & Pick<IRequestData, 'id'>;

type RestOf<T extends IRequestData | NewRequestData> = Omit<T, 'createdTime'> & {
  createdTime?: string | null;
};

export type RestRequestData = RestOf<IRequestData>;

export type NewRestRequestData = RestOf<NewRequestData>;

export type PartialUpdateRestRequestData = RestOf<PartialUpdateRequestData>;

export type EntityResponseType = HttpResponse<IRequestData>;
export type EntityArrayResponseType = HttpResponse<IRequestData[]>;

@Injectable({ providedIn: 'root' })
export class RequestDataService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-data');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(requestData: NewRequestData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestData);
    return this.http
      .post<RestRequestData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestData: IRequestData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestData);
    return this.http
      .put<RestRequestData>(`${this.resourceUrl}/${this.getRequestDataIdentifier(requestData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestData: PartialUpdateRequestData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestData);
    return this.http
      .patch<RestRequestData>(`${this.resourceUrl}/${this.getRequestDataIdentifier(requestData)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestDataIdentifier(requestData: Pick<IRequestData, 'id'>): string {
    return requestData.id;
  }

  compareRequestData(o1: Pick<IRequestData, 'id'> | null, o2: Pick<IRequestData, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestDataIdentifier(o1) === this.getRequestDataIdentifier(o2) : o1 === o2;
  }

  addRequestDataToCollectionIfMissing<Type extends Pick<IRequestData, 'id'>>(
    requestDataCollection: Type[],
    ...requestDataToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestData: Type[] = requestDataToCheck.filter(isPresent);
    if (requestData.length > 0) {
      const requestDataCollectionIdentifiers = requestDataCollection.map(
        requestDataItem => this.getRequestDataIdentifier(requestDataItem)!,
      );
      const requestDataToAdd = requestData.filter(requestDataItem => {
        const requestDataIdentifier = this.getRequestDataIdentifier(requestDataItem);
        if (requestDataCollectionIdentifiers.includes(requestDataIdentifier)) {
          return false;
        }
        requestDataCollectionIdentifiers.push(requestDataIdentifier);
        return true;
      });
      return [...requestDataToAdd, ...requestDataCollection];
    }
    return requestDataCollection;
  }

  protected convertDateFromClient<T extends IRequestData | NewRequestData | PartialUpdateRequestData>(requestData: T): RestOf<T> {
    return {
      ...requestData,
      createdTime: requestData.createdTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restRequestData: RestRequestData): IRequestData {
    return {
      ...restRequestData,
      createdTime: restRequestData.createdTime ? dayjs(restRequestData.createdTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestData>): HttpResponse<IRequestData> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestData[]>): HttpResponse<IRequestData[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
