import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChannelUserCredentials, NewChannelUserCredentials } from '../channel-user-credentials.model';

export type PartialUpdateChannelUserCredentials = Partial<IChannelUserCredentials> & Pick<IChannelUserCredentials, 'id'>;

export type EntityResponseType = HttpResponse<IChannelUserCredentials>;
export type EntityArrayResponseType = HttpResponse<IChannelUserCredentials[]>;

@Injectable({ providedIn: 'root' })
export class ChannelUserCredentialsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/channel-user-credentials');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(channelUserCredentials: NewChannelUserCredentials): Observable<EntityResponseType> {
    return this.http.post<IChannelUserCredentials>(this.resourceUrl, channelUserCredentials, { observe: 'response' });
  }

  update(channelUserCredentials: IChannelUserCredentials): Observable<EntityResponseType> {
    return this.http.put<IChannelUserCredentials>(
      `${this.resourceUrl}/${this.getChannelUserCredentialsIdentifier(channelUserCredentials)}`,
      channelUserCredentials,
      { observe: 'response' },
    );
  }

  partialUpdate(channelUserCredentials: PartialUpdateChannelUserCredentials): Observable<EntityResponseType> {
    return this.http.patch<IChannelUserCredentials>(
      `${this.resourceUrl}/${this.getChannelUserCredentialsIdentifier(channelUserCredentials)}`,
      channelUserCredentials,
      { observe: 'response' },
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IChannelUserCredentials>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChannelUserCredentials[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChannelUserCredentialsIdentifier(channelUserCredentials: Pick<IChannelUserCredentials, 'id'>): string {
    return channelUserCredentials.id;
  }

  compareChannelUserCredentials(o1: Pick<IChannelUserCredentials, 'id'> | null, o2: Pick<IChannelUserCredentials, 'id'> | null): boolean {
    return o1 && o2 ? this.getChannelUserCredentialsIdentifier(o1) === this.getChannelUserCredentialsIdentifier(o2) : o1 === o2;
  }

  addChannelUserCredentialsToCollectionIfMissing<Type extends Pick<IChannelUserCredentials, 'id'>>(
    channelUserCredentialsCollection: Type[],
    ...channelUserCredentialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const channelUserCredentials: Type[] = channelUserCredentialsToCheck.filter(isPresent);
    if (channelUserCredentials.length > 0) {
      const channelUserCredentialsCollectionIdentifiers = channelUserCredentialsCollection.map(
        channelUserCredentialsItem => this.getChannelUserCredentialsIdentifier(channelUserCredentialsItem)!,
      );
      const channelUserCredentialsToAdd = channelUserCredentials.filter(channelUserCredentialsItem => {
        const channelUserCredentialsIdentifier = this.getChannelUserCredentialsIdentifier(channelUserCredentialsItem);
        if (channelUserCredentialsCollectionIdentifiers.includes(channelUserCredentialsIdentifier)) {
          return false;
        }
        channelUserCredentialsCollectionIdentifiers.push(channelUserCredentialsIdentifier);
        return true;
      });
      return [...channelUserCredentialsToAdd, ...channelUserCredentialsCollection];
    }
    return channelUserCredentialsCollection;
  }
}
