import { IRequestData } from 'app/entities/request-data/request-data.model';
import { IChannelUserCredentials } from 'app/entities/channel-user-credentials/channel-user-credentials.model';
import { MessageStatus } from 'app/entities/enumerations/message-status.model';
import { IUser } from '../user/user.model';

export interface INotification {
  id: string;
  content?: string | null;
  status?: keyof typeof MessageStatus | null;
  recipient?: IUser | null;
  requestId?: IRequestData | null;
  channel?: IChannelUserCredentials | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
