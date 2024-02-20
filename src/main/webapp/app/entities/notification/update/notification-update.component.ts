import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUserInternal } from 'app/entities/user-internal/user-internal.model';
import { UserInternalService } from 'app/entities/user-internal/service/user-internal.service';
import { IRequestData } from 'app/entities/request-data/request-data.model';
import { RequestDataService } from 'app/entities/request-data/service/request-data.service';
import { IChannelUserCredentials } from 'app/entities/channel-user-credentials/channel-user-credentials.model';
import { ChannelUserCredentialsService } from 'app/entities/channel-user-credentials/service/channel-user-credentials.service';
import { MessageStatus } from 'app/entities/enumerations/message-status.model';
import { NotificationService } from '../service/notification.service';
import { INotification } from '../notification.model';
import { NotificationFormService, NotificationFormGroup } from './notification-form.service';

@Component({
  standalone: true,
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  notification: INotification | null = null;
  messageStatusValues = Object.keys(MessageStatus);

  recipientsCollection: IUserInternal[] = [];
  requestIdsCollection: IRequestData[] = [];
  channelsCollection: IChannelUserCredentials[] = [];

  editForm: NotificationFormGroup = this.notificationFormService.createNotificationFormGroup();

  constructor(
    protected notificationService: NotificationService,
    protected notificationFormService: NotificationFormService,
    protected userInternalService: UserInternalService,
    protected requestDataService: RequestDataService,
    protected channelUserCredentialsService: ChannelUserCredentialsService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareUserInternal = (o1: IUserInternal | null, o2: IUserInternal | null): boolean =>
    this.userInternalService.compareUserInternal(o1, o2);

  compareRequestData = (o1: IRequestData | null, o2: IRequestData | null): boolean => this.requestDataService.compareRequestData(o1, o2);

  compareChannelUserCredentials = (o1: IChannelUserCredentials | null, o2: IChannelUserCredentials | null): boolean =>
    this.channelUserCredentialsService.compareChannelUserCredentials(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      this.notification = notification;
      if (notification) {
        this.updateForm(notification);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notification = this.notificationFormService.getNotification(this.editForm);
    if (notification.id !== null) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>): void {
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

  protected updateForm(notification: INotification): void {
    this.notification = notification;
    this.notificationFormService.resetForm(this.editForm, notification);

    this.recipientsCollection = this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(
      this.recipientsCollection,
      notification.recipient,
    );
    this.requestIdsCollection = this.requestDataService.addRequestDataToCollectionIfMissing<IRequestData>(
      this.requestIdsCollection,
      notification.requestId,
    );
    this.channelsCollection = this.channelUserCredentialsService.addChannelUserCredentialsToCollectionIfMissing<IChannelUserCredentials>(
      this.channelsCollection,
      notification.channel,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userInternalService
      .query({ 'notificationId.specified': 'false' })
      .pipe(map((res: HttpResponse<IUserInternal[]>) => res.body ?? []))
      .pipe(
        map((userInternals: IUserInternal[]) =>
          this.userInternalService.addUserInternalToCollectionIfMissing<IUserInternal>(userInternals, this.notification?.recipient),
        ),
      )
      .subscribe((userInternals: IUserInternal[]) => (this.recipientsCollection = userInternals));

    this.requestDataService
      .query({ 'notificationId.specified': 'false' })
      .pipe(map((res: HttpResponse<IRequestData[]>) => res.body ?? []))
      .pipe(
        map((requestData: IRequestData[]) =>
          this.requestDataService.addRequestDataToCollectionIfMissing<IRequestData>(requestData, this.notification?.requestId),
        ),
      )
      .subscribe((requestData: IRequestData[]) => (this.requestIdsCollection = requestData));

    this.channelUserCredentialsService
      .query({ 'notificationId.specified': 'false' })
      .pipe(map((res: HttpResponse<IChannelUserCredentials[]>) => res.body ?? []))
      .pipe(
        map((channelUserCredentials: IChannelUserCredentials[]) =>
          this.channelUserCredentialsService.addChannelUserCredentialsToCollectionIfMissing<IChannelUserCredentials>(
            channelUserCredentials,
            this.notification?.channel,
          ),
        ),
      )
      .subscribe((channelUserCredentials: IChannelUserCredentials[]) => (this.channelsCollection = channelUserCredentials));
  }
}
