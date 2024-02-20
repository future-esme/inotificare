import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRequestData, NewRequestData } from '../request-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestData for edit and NewRequestDataFormGroupInput for create.
 */
type RequestDataFormGroupInput = IRequestData | PartialWithRequiredKeyOf<NewRequestData>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IRequestData | NewRequestData> = Omit<T, 'createdTime'> & {
  createdTime?: string | null;
};

type RequestDataFormRawValue = FormValueOf<IRequestData>;

type NewRequestDataFormRawValue = FormValueOf<NewRequestData>;

type RequestDataFormDefaults = Pick<NewRequestData, 'id' | 'createdTime'>;

type RequestDataFormGroupContent = {
  id: FormControl<RequestDataFormRawValue['id'] | NewRequestData['id']>;
  channel: FormControl<RequestDataFormRawValue['channel']>;
  recipients: FormControl<RequestDataFormRawValue['recipients']>;
  recipientType: FormControl<RequestDataFormRawValue['recipientType']>;
  priority: FormControl<RequestDataFormRawValue['priority']>;
  content: FormControl<RequestDataFormRawValue['content']>;
  messageStatus: FormControl<RequestDataFormRawValue['messageStatus']>;
  createdTime: FormControl<RequestDataFormRawValue['createdTime']>;
  templateId: FormControl<RequestDataFormRawValue['templateId']>;
};

export type RequestDataFormGroup = FormGroup<RequestDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestDataFormService {
  createRequestDataFormGroup(requestData: RequestDataFormGroupInput = { id: null }): RequestDataFormGroup {
    const requestDataRawValue = this.convertRequestDataToRequestDataRawValue({
      ...this.getFormDefaults(),
      ...requestData,
    });
    return new FormGroup<RequestDataFormGroupContent>({
      id: new FormControl(
        { value: requestDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      channel: new FormControl(requestDataRawValue.channel, {
        validators: [Validators.required],
      }),
      recipients: new FormControl(requestDataRawValue.recipients),
      recipientType: new FormControl(requestDataRawValue.recipientType),
      priority: new FormControl(requestDataRawValue.priority),
      content: new FormControl(requestDataRawValue.content),
      messageStatus: new FormControl(requestDataRawValue.messageStatus, {
        validators: [Validators.required],
      }),
      createdTime: new FormControl(requestDataRawValue.createdTime, {
        validators: [Validators.required],
      }),
      templateId: new FormControl(requestDataRawValue.templateId),
    });
  }

  getRequestData(form: RequestDataFormGroup): IRequestData | NewRequestData {
    return this.convertRequestDataRawValueToRequestData(form.getRawValue() as RequestDataFormRawValue | NewRequestDataFormRawValue);
  }

  resetForm(form: RequestDataFormGroup, requestData: RequestDataFormGroupInput): void {
    const requestDataRawValue = this.convertRequestDataToRequestDataRawValue({ ...this.getFormDefaults(), ...requestData });
    form.reset(
      {
        ...requestDataRawValue,
        id: { value: requestDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestDataFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdTime: currentTime,
    };
  }

  private convertRequestDataRawValueToRequestData(
    rawRequestData: RequestDataFormRawValue | NewRequestDataFormRawValue,
  ): IRequestData | NewRequestData {
    return {
      ...rawRequestData,
      createdTime: dayjs(rawRequestData.createdTime, DATE_TIME_FORMAT),
    };
  }

  private convertRequestDataToRequestDataRawValue(
    requestData: IRequestData | (Partial<NewRequestData> & RequestDataFormDefaults),
  ): RequestDataFormRawValue | PartialWithRequiredKeyOf<NewRequestDataFormRawValue> {
    return {
      ...requestData,
      createdTime: requestData.createdTime ? requestData.createdTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
