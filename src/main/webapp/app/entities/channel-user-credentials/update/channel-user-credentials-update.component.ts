import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Channel } from 'app/entities/enumerations/channel.model';
import { IChannelUserCredentials } from '../channel-user-credentials.model';
import { ChannelUserCredentialsService } from '../service/channel-user-credentials.service';
import { ChannelUserCredentialsFormService, ChannelUserCredentialsFormGroup } from './channel-user-credentials-form.service';

@Component({
  standalone: true,
  selector: 'jhi-channel-user-credentials-update',
  templateUrl: './channel-user-credentials-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ChannelUserCredentialsUpdateComponent implements OnInit {
  isSaving = false;
  channelUserCredentials: IChannelUserCredentials | null = null;
  channelValues = Object.keys(Channel);

  editForm: ChannelUserCredentialsFormGroup = this.channelUserCredentialsFormService.createChannelUserCredentialsFormGroup();

  constructor(
    protected channelUserCredentialsService: ChannelUserCredentialsService,
    protected channelUserCredentialsFormService: ChannelUserCredentialsFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ channelUserCredentials }) => {
      this.channelUserCredentials = channelUserCredentials;
      if (channelUserCredentials) {
        this.updateForm(channelUserCredentials);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const channelUserCredentials = this.channelUserCredentialsFormService.getChannelUserCredentials(this.editForm);
    if (channelUserCredentials.id !== null) {
      this.subscribeToSaveResponse(this.channelUserCredentialsService.update(channelUserCredentials));
    } else {
      this.subscribeToSaveResponse(this.channelUserCredentialsService.create(channelUserCredentials));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChannelUserCredentials>>): void {
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

  protected updateForm(channelUserCredentials: IChannelUserCredentials): void {
    this.channelUserCredentials = channelUserCredentials;
    this.channelUserCredentialsFormService.resetForm(this.editForm, channelUserCredentials);
  }
}
