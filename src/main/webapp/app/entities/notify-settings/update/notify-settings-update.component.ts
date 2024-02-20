import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IChannelUserCredentials } from 'app/entities/channel-user-credentials/channel-user-credentials.model';
import { ChannelUserCredentialsService } from 'app/entities/channel-user-credentials/service/channel-user-credentials.service';
import { IUserInternal } from 'app/entities/user-internal/user-internal.model';
import { UserInternalService } from 'app/entities/user-internal/service/user-internal.service';
import { Channel } from 'app/entities/enumerations/channel.model';
import { NotifyChannelStatusEnum } from 'app/entities/enumerations/notify-channel-status-enum.model';
import { NotifySettingsService } from '../service/notify-settings.service';
import { INotifySettings } from '../notify-settings.model';
import { NotifySettingsFormService, NotifySettingsFormGroup } from './notify-settings-form.service';

@Component({
  standalone: true,
  selector: 'jhi-notify-settings-update',
  templateUrl: './notify-settings-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NotifySettingsUpdateComponent implements OnInit {
  isSaving = false;
  notifySettings: INotifySettings | null = null;
  channelValues = Object.keys(Channel);
  notifyChannelStatusEnumValues = Object.keys(NotifyChannelStatusEnum);

  credentialsCollection: IChannelUserCredentials[] = [];
  userInternalsSharedCollection: IUserInternal[] = [];

  editForm: NotifySettingsFormGroup = this.notifySettingsFormService.createNotifySettingsFormGroup();

  constructor(
    protected notifySettingsService: NotifySettingsService,
    protected notifySettingsFormService: NotifySettingsFormService,
    protected channelUserCredentialsService: ChannelUserCredentialsService,
    protected userInternalService: UserInternalService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareChannelUserCredentials = (o1: IChannelUserCredentials | null, o2: IChannelUserCredentials | null): boolean =>
    this.channelUserCredentialsService.compareChannelUserCredentials(o1, o2);

  compareUserInternal = (o1: IUserInternal | null, o2: IUserInternal | null): boolean =>
    this.userInternalService.compareUserInternal(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notifySettings }) => {
      this.notifySettings = notifySettings;
      if (notifySettings) {
        this.updateForm(notifySettings);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notifySettings = this.notifySettingsFormService.getNotifySettings(this.editForm);
    if (notifySettings.id !== null) {
      this.subscribeToSaveResponse(this.notifySettingsService.update(notifySettings));
    } else {
      this.subscribeToSaveResponse(this.notifySettingsService.create(notifySettings));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotifySettings>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(notifySettings: INotifySettings): void {
    this.notifySettings = notifySettings;
    this.notifySettingsFormService.resetForm(this.editForm, notifySettings);

    this.credentialsCollection = this.channelUserCredentialsService.addChannelUserCredentialsToCollectionIfMissing<IChannelUserCredentials>(
      this.credentialsCollection,
      notifySettings.credentials,
    );
    this.userInternalsSharedCollection = this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(
      this.userInternalsSharedCollection,
      notifySettings.userInternal,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.channelUserCredentialsService
      .query({ 'notifySettingsId.specified': 'false' })
      .pipe(map((res: HttpResponse<IChannelUserCredentials[]>) => res.body ?? []))
      .pipe(
        map((channelUserCredentials: IChannelUserCredentials[]) =>
          this.channelUserCredentialsService.addChannelUserCredentialsToCollectionIfMissing<IChannelUserCredentials>(
            channelUserCredentials,
            this.notifySettings?.credentials,
          ),
        ),
      )
      .subscribe((channelUserCredentials: IChannelUserCredentials[]) => (this.credentialsCollection = channelUserCredentials));

    this.userInternalService
      .query()
      .pipe(map((res: HttpResponse<IUserInternal[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUserInternal[]) =>
          this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(userInternals, this.notifySettings?.userInternal),
        ),
      )
      .subscribe((userInternals: IUserInternal[]) => (this.userInternalsSharedCollection = userInternals));
  }
}
