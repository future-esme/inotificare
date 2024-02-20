import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  id: '36476260-c719-4b11-bed5-fe20779d162c',
  title: 'yahoo',
};

export const sampleWithPartialData: IDepartment = {
  id: '8875ff1c-607c-457c-bb84-b3086e952c54',
  title: 'handle ugh ugh',
};

export const sampleWithFullData: IDepartment = {
  id: '581c4cb7-7a17-4462-94a2-add089b037c6',
  title: 'scaly attract',
};

export const sampleWithNewData: NewDepartment = {
  title: 'lay gadzooks defiantly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
