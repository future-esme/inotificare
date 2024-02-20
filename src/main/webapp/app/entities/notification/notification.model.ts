import { IUserInternal } from 'app/entities/user-internal/user-internal.model';
import { IRequestData } from 'app/entities/request-data/request-data.model';
import { IChannelUserCredentials } from 'app/entities/channel-user-credentials/channel-user-credentials.model';
import { MessageStatus } from 'app/entities/enumerations/message-status.model';

export interface INotification {
  id: string;
  content?: string | null;
  status?: keyof typeof MessageStatus | null;
  recipient?: IUserInternal | null;
  requestId?: IRequestData | null;
  channel?: IChannelUserCredentials | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
