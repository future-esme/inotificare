import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IChannelUserCredentials } from '../channel-user-credentials.model';

@Component({
  standalone: true,
  selector: 'jhi-channel-user-credentials-detail',
  templateUrl: './channel-user-credentials-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ChannelUserCredentialsDetailComponent {
  @Input() channelUserCredentials: IChannelUserCredentials | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
