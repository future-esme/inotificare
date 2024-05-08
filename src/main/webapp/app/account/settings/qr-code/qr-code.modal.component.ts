import { Component, OnInit } from '@angular/core';
import SharedModule from '../../../shared/shared.module';
import { Channel } from '../../../entities/enumerations/channel.model';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { SettingsService } from '../settings.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';

@Component({
  standalone: true,
  templateUrl: './qr-code.modal.component.html',
  imports: [SharedModule],
})
export class QrCodeModalComponent implements OnInit {
  channel: Channel | undefined;
  imageUrl: SafeUrl | undefined;

  constructor(
    private sanitizer: DomSanitizer,
    private settingsService: SettingsService,
    private activeModal: NgbActiveModal,
  ) {}

  ngOnInit() {
    if (this.channel) {
      this.settingsService.getQrFile(this.channel).subscribe((baseImage: HttpResponse<Blob>) => {
        adaptResultToBase64(baseImage.body!).then(res => {
          this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(res);
        });
      });
    } else {
      this.cancel();
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
function adaptResultToBase64(res: Blob): Promise<string> {
  let reader: FileReader = new FileReader();

  return new Promise((resolve, reject) => {
    reader.onloadend = () => {
      resolve(reader.result as string);
    };
    reader.onerror = () => {
      reject('Error reading file.');
    };
    reader.readAsDataURL(res);
  });
}
