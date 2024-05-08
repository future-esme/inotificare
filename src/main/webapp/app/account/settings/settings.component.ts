import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { User } from '../../entities/user/user.model';
import { AccountService } from '../../core/auth/account.service';
import SortByDirective from '../../shared/sort/sort-by.directive';
import SortDirective from '../../shared/sort/sort.directive';
import { ChannelToken, INotifySettings } from '../../entities/notify-settings/notify-settings.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import AddChannelDialogComponent from './add-channel/add-channel-dialog.component';
import { ActiveChannel } from '../../entities/enumerations/channel.model';
import dayjs from 'dayjs/esm';
import { QrCodeModalComponent } from './qr-code/qr-code.modal.component';
import { NotifyChannelStatusEnum } from '../../entities/enumerations/notify-channel-status-enum.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-settings',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, SortByDirective, SortDirective],
  templateUrl: './settings.component.html',
})
export default class SettingsComponent implements OnInit {
  success = false;
  userProfile: User | null = null;
  editForm = this.fb.group({
    code: new FormControl('', {
      validators: [Validators.maxLength(6), Validators.required],
    }),
  });
  protected channels: string[] = Object.values(ActiveChannel);

  constructor(
    private accountService: AccountService,
    private modalService: NgbModal,
    private router: Router,
    protected fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.getUserProfile();
  }

  trackId = (_index: number, item: INotifySettings): string => item.id;

  addChannel(): void {
    const modalRef = this.modalService.open(AddChannelDialogComponent, { size: 'md', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason) {
        this.getUserProfile();
      }
    });
  }

  hasToken(notifySettings: INotifySettings): boolean {
    return (
      notifySettings.channelsToken != undefined &&
      dayjs(notifySettings.channelsToken.expirationTime).isAfter(new Date()) &&
      notifySettings.status !== NotifyChannelStatusEnum.ON
    );
  }

  changeHidden(channelToken?: ChannelToken): void {
    if (channelToken) {
      channelToken.isHidden = !channelToken.isHidden;
    }
  }

  displayQrCode(channel: string): void {
    if (channel === 'VIBER' || channel === 'TELEGRAM') {
      const modalRef = this.modalService.open(QrCodeModalComponent, { size: 'md', backdrop: 'static' });
      modalRef.componentInstance.channel = channel;
      modalRef.closed.subscribe(() => {});
    }
  }

  sendEmailToken(): void {
    const code = this.editForm.get('code')?.value;
    if (code) {
      this.accountService.emailActivate(code).subscribe(() => {
        this.getUserProfile();
      });
    }
  }

  private getUserProfile(): void {
    this.accountService.getUserProfile().subscribe(profile => {
      this.userProfile = profile;
    });
  }
}
