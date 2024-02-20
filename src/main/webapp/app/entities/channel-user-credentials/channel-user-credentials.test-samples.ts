import { IChannelUserCredentials, NewChannelUserCredentials } from './channel-user-credentials.model';

export const sampleWithRequiredData: IChannelUserCredentials = {
  id: 'd4e75777-1149-4920-8376-38e4a8d77da6',
};

export const sampleWithPartialData: IChannelUserCredentials = {
  id: '7c07dc5b-fead-404d-bddb-7f7d3e3f85c9',
};

export const sampleWithFullData: IChannelUserCredentials = {
  id: '2a2d94f9-c7eb-4a14-9857-78efb80fa911',
  chatId: 'despite lad',
  channel: 'EMAIL',
};

export const sampleWithNewData: NewChannelUserCredentials = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
