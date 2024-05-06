import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Channel } from '../../entities/enumerations/channel.model';
import { IUser } from '../../entities/user/user.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SettingsService {
  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
  ) {}

  createChannel(channel: Channel, email?: string): Observable<HttpResponse<IUser | null>> {
    let options: HttpParams = new HttpParams();
    if (email) {
      options = options.append('email', email);
    }
    return this.http.get<IUser>(this.applicationConfigService.getEndpointFor(`api/notify-settings/add-channel/${channel}`), {
      params: options,
      observe: 'response',
    });
  }

  getQrFile(channel: Channel): Observable<any> {
    return this.http.get(`api/notify-settings/qr-code/${channel}`, { responseType: 'blob', observe: 'response' });
  }
}
