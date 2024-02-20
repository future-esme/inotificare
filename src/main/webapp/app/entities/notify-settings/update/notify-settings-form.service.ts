import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotifySettings, NewNotifySettings } from '../notify-settings.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotifySettings for edit and NewNotifySettingsFormGroupInput for create.
 */
type NotifySettingsFormGroupInput = INotifySettings | PartialWithRequiredKeyOf<NewNotifySettings>;

type NotifySettingsFormDefaults = Pick<NewNotifySettings, 'id'>;

type NotifySettingsFormGroupContent = {
  id: FormControl<INotifySettings['id'] | NewNotifySettings['id']>;
  channel: FormControl<INotifySettings['channel']>;
  status: FormControl<INotifySettings['status']>;
  credentials: FormControl<INotifySettings['credentials']>;
  userInternal: FormControl<INotifySettings['userInternal']>;
};

export type NotifySettingsFormGroup = FormGroup<NotifySettingsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotifySettingsFormService {
  createNotifySettingsFormGroup(notifySettings: NotifySettingsFormGroupInput = { id: null }): NotifySettingsFormGroup {
    const notifySettingsRawValue = {
      ...this.getFormDefaults(),
      ...notifySettings,
    };
    return new FormGroup<NotifySettingsFormGroupContent>({
      id: new FormControl(
        { value: notifySettingsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      channel: new FormControl(notifySettingsRawValue.channel),
      status: new FormControl(notifySettingsRawValue.status),
      credentials: new FormControl(notifySettingsRawValue.credentials),
      userInternal: new FormControl(notifySettingsRawValue.userInternal),
    });
  }

  getNotifySettings(form: NotifySettingsFormGroup): INotifySettings | NewNotifySettings {
    return form.getRawValue() as INotifySettings | NewNotifySettings;
  }

  resetForm(form: NotifySettingsFormGroup, notifySettings: NotifySettingsFormGroupInput): void {
    const notifySettingsRawValue = { ...this.getFormDefaults(), ...notifySettings };
    form.reset(
      {
        ...notifySettingsRawValue,
        id: { value: notifySettingsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): NotifySettingsFormDefaults {
    return {
      id: null,
    };
  }
}
