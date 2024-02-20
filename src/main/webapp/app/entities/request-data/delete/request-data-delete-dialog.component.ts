import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestData } from '../request-data.model';
import { RequestDataService } from '../service/request-data.service';

@Component({
  standalone: true,
  templateUrl: './request-data-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestDataDeleteDialogComponent {
  requestData?: IRequestData;

  constructor(
    protected requestDataService: RequestDataService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.requestDataService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
