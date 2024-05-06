import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { SettingsService } from '../settings.service';
import { ActiveChannel, Channel } from '../../../entities/enumerations/channel.model';

@Component({
  standalone: true,
  selector: 'add-channel',
  templateUrl: './add-channel-dialog.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export default class AddChannelDialogComponent {
  channels = Object.values(ActiveChannel);
  channelEmail = Channel.EMAIL;
  isSaving = false;
  editForm = new FormGroup({
    channel: new FormControl(this.channelEmail, {
      nonNullable: true,
      validators: [Validators.required],
    }),
    email: new FormControl('', {
      nonNullable: false,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.email],
    }),
  });

  constructor(
    private activeModal: NgbActiveModal,
    private notifySettings: SettingsService,
  ) {}

  onSelectChannel(): void {
    if (this.editForm.get('channel')?.value == Channel.EMAIL) {
      this.editForm
        .get('email')
        ?.addValidators([Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.email]);
    } else {
      this.editForm
        .get('email')
        ?.removeValidators([Validators.required, Validators.minLength(1), Validators.maxLength(100), Validators.email]);
      this.editForm.get('email')?.setErrors(null);
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmCreate(): void {
    let email: string | undefined;
    this.isSaving = true;
    if (this.editForm.get('channel')?.value == this.channelEmail) {
      email = this.editForm.get('email')?.value!;
    }
    this.notifySettings.createChannel(this.editForm.get('channel')?.value!, email).subscribe(
      userProfile => {
        this.activeModal.close(userProfile);
      },
      error => {
        this.isSaving = false;
      },
    );
  }
}
