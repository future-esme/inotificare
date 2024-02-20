import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: '73edbc01-34b8-48f6-abbc-01ac9cd8b700',
};

export const sampleWithPartialData: INotification = {
  id: '09a12c69-1eab-4335-8e91-b7bfaa23e329',
};

export const sampleWithFullData: INotification = {
  id: '001286f9-0c6b-4d43-b5ae-27f8fe2c039b',
  content: 'that but',
  status: 'UNPROCESSED',
};

export const sampleWithNewData: NewNotification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
