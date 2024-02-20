import dayjs from 'dayjs/esm';

import { IRequestData, NewRequestData } from './request-data.model';

export const sampleWithRequiredData: IRequestData = {
  id: 'db220ae4-0930-4631-9c6a-10abc6cb6c4d',
  channel: 'DEFAULT',
  messageStatus: 'SENT',
  createdTime: dayjs('2023-12-28T06:38'),
};

export const sampleWithPartialData: IRequestData = {
  id: '7cf88ddf-1a68-4d85-96fb-f5ff3d8e95c5',
  channel: 'DEFAULT',
  priority: 'MEDIUM',
  content: 'after until tire',
  messageStatus: 'PENDING',
  createdTime: dayjs('2023-12-28T16:03'),
};

export const sampleWithFullData: IRequestData = {
  id: '2b962b43-05a2-4a74-943d-daf0603138c8',
  channel: 'TELEGRAM',
  recipients: 'which when upward',
  recipientType: 'DEPARTMENT',
  priority: 'MEDIUM',
  content: 'bibliography intermarry',
  messageStatus: 'UNPROCESSED',
  createdTime: dayjs('2023-12-28T00:17'),
};

export const sampleWithNewData: NewRequestData = {
  channel: 'TELEGRAM',
  messageStatus: 'SENT',
  createdTime: dayjs('2023-12-28T13:13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
