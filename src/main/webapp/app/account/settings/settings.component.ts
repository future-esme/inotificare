import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { User } from '../../entities/user/user.model';
import { AccountService } from '../../core/auth/account.service';
import SortByDirective from '../../shared/sort/sort-by.directive';
import SortDirective from '../../shared/sort/sort.directive';
import { ChannelToken, INotifySettings } from '../../entities/notify-settings/notify-settings.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import AddChannelDialogComponent from './add-channel/add-channel-dialog.component';
import { Channel } from '../../entities/enumerations/channel.model';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-settings',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, SortByDirective, SortDirective],
  templateUrl: './settings.component.html',
})
export default class SettingsComponent implements OnInit {
  success = false;
  userProfile: User | null = null;

  constructor(
    private accountService: AccountService,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.accountService.getUserProfile().subscribe(profile => {
      this.userProfile = profile;
    });
  }

  trackId = (_index: number, item: INotifySettings): string => item.id;

  addChannel(): void {
    const modalRef = this.modalService.open(AddChannelDialogComponent, { size: 'md', backdrop: 'static' });
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason) {
        this.userProfile = reason;
      }
    });
  }

  hasToken(channel: Channel, channelToken?: ChannelToken): boolean {
    return channelToken != undefined && channel !== Channel.EMAIL && dayjs(channelToken.expirationTime).isAfter(new Date());
  }

  changeHidden(channelToken?: ChannelToken): void {
    if (channelToken) {
      channelToken.isHidden = !channelToken.isHidden;
    }
  }
}
