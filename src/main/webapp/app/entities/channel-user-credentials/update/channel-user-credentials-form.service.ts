import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IChannelUserCredentials, NewChannelUserCredentials } from '../channel-user-credentials.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IChannelUserCredentials for edit and NewChannelUserCredentialsFormGroupInput for create.
 */
type ChannelUserCredentialsFormGroupInput = IChannelUserCredentials | PartialWithRequiredKeyOf<NewChannelUserCredentials>;

type ChannelUserCredentialsFormDefaults = Pick<NewChannelUserCredentials, 'id'>;

type ChannelUserCredentialsFormGroupContent = {
  id: FormControl<IChannelUserCredentials['id'] | NewChannelUserCredentials['id']>;
  chatId: FormControl<IChannelUserCredentials['chatId']>;
  channel: FormControl<IChannelUserCredentials['channel']>;
};

export type ChannelUserCredentialsFormGroup = FormGroup<ChannelUserCredentialsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ChannelUserCredentialsFormService {
  createChannelUserCredentialsFormGroup(
    channelUserCredentials: ChannelUserCredentialsFormGroupInput = { id: null },
  ): ChannelUserCredentialsFormGroup {
    const channelUserCredentialsRawValue = {
      ...this.getFormDefaults(),
      ...channelUserCredentials,
    };
    return new FormGroup<ChannelUserCredentialsFormGroupContent>({
      id: new FormControl(
        { value: channelUserCredentialsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      chatId: new FormControl(channelUserCredentialsRawValue.chatId),
      channel: new FormControl(channelUserCredentialsRawValue.channel),
    });
  }

  getChannelUserCredentials(form: ChannelUserCredentialsFormGroup): IChannelUserCredentials | NewChannelUserCredentials {
    return form.getRawValue() as IChannelUserCredentials | NewChannelUserCredentials;
  }

  resetForm(form: ChannelUserCredentialsFormGroup, channelUserCredentials: ChannelUserCredentialsFormGroupInput): void {
    const channelUserCredentialsRawValue = { ...this.getFormDefaults(), ...channelUserCredentials };
    form.reset(
      {
        ...channelUserCredentialsRawValue,
        id: { value: channelUserCredentialsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ChannelUserCredentialsFormDefaults {
    return {
      id: null,
    };
  }
}
