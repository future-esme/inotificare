import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IChannelUserCredentials } from '../channel-user-credentials.model';
import { ChannelUserCredentialsService } from '../service/channel-user-credentials.service';

@Component({
  standalone: true,
  templateUrl: './channel-user-credentials-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ChannelUserCredentialsDeleteDialogComponent {
  channelUserCredentials?: IChannelUserCredentials;

  constructor(
    protected channelUserCredentialsService: ChannelUserCredentialsService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.channelUserCredentialsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
